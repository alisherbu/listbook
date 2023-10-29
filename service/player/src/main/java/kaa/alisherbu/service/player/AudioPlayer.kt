package kaa.alisherbu.service.player

import android.annotation.SuppressLint
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kaa.alisherbu.listbook.core.shared.coroutine.AppDispatchers
import kaa.alisherbu.listbook.core.shared.model.Chapter
import kaa.alisherbu.listbook.core.shared.player.tickerFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@SuppressLint("UnsafeOptInUsageError")
class AudioPlayer @Inject constructor(
    private val exoPlayer: ExoPlayer,
    dispatchers: AppDispatchers,
    private val audioDownloadManager: AudioDownloadManager,
    private val downloadTracker: DownloadTracker
) {
    private val medias: MutableList<Pair<Chapter, MediaItem>> = mutableListOf()
    private val mainScope = CoroutineScope(dispatchers.main)

    init {
        exoPlayer.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                _isPlaying.tryEmit(isPlaying)
            }

            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                medias.find { it.second == mediaItem }?.first?.let(_currentChapter::tryEmit)
            }
        })
        tickerFlow(1000.milliseconds).onEach {
            val position = exoPlayer.currentPosition
            _currentPosition.tryEmit(position)
            _duration.tryEmit(exoPlayer.duration)
        }.launchIn(mainScope)
    }

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _currentChapter = MutableStateFlow<Chapter?>(null)
    val currentChapter: StateFlow<Chapter?> = _currentChapter.asStateFlow()

    private val _currentPosition = MutableStateFlow(0L)
    val currentPosition: StateFlow<Long> = _currentPosition.asStateFlow()

    private val _duration = MutableStateFlow(0L)
    val duration: StateFlow<Long> = _duration.asStateFlow()

    fun loadChapters(chapters: List<Chapter>) {
        medias.clear()
        chapters.forEach {
            medias.add(Pair(it, getAsMedia(it)))
        }
        exoPlayer.setMediaItems(medias.map { it.second }, false)
        exoPlayer.prepare()
        exoPlayer.play()
    }

    fun playOrPause() {
        if (isPlaying.value) {
            exoPlayer.pause()
        } else {
            exoPlayer.play()
        }
    }

    fun previous() {
        exoPlayer.seekToPrevious()
    }

    fun next() {
        exoPlayer.seekToNext()
    }

    fun play(audioBook: Chapter) {
        val index = medias.map { it.first }.indexOf(audioBook)
        exoPlayer.seekTo(index, 0L)
        if (!isPlaying.value) {
            exoPlayer.play()
        }
    }

    fun seekTo(position: Long) {
        exoPlayer.seekTo(position)
    }

    private fun getAsMedia(audioBook: Chapter): MediaItem {
        val downloadRequest = audioDownloadManager.getDownload(audioBook.id)?.request
        return downloadRequest?.toMediaItem() ?: audioBook.toMediaItem()
    }

    private fun Chapter.toMediaItem(): MediaItem {
        val metadata = MediaMetadata.Builder()
            .setTitle(name)
            .build()
        return MediaItem.Builder()
            .setMediaId(id)
            .setMediaMetadata(metadata)
            .setUri(audioUrl)
            .build()
    }

    fun removeDownload(audioBook: Chapter) {
        downloadTracker.removeDownload(audioBook.id)
    }

    fun download(audioBook: Chapter) {
        downloadTracker.startDownload(audioBook.toMediaItem())
    }
}
