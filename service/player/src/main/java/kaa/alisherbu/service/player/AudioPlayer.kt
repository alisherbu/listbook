package kaa.alisherbu.service.player

import android.annotation.SuppressLint
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kaa.alisherbu.listbook.core.shared.coroutine.AppDispatchers
import kaa.alisherbu.listbook.core.shared.model.AudioBook
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
    private val medias: MutableList<Pair<AudioBook, MediaItem>> = mutableListOf()
    private val mainScope = CoroutineScope(dispatchers.main)

    init {
        exoPlayer.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                _isPlaying.tryEmit(isPlaying)
            }

            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                medias.find { it.second == mediaItem }?.first?.let(_currentAudioBook::tryEmit)
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

    private val _currentAudioBook = MutableStateFlow<AudioBook?>(null)
    val currentAudioBook: StateFlow<AudioBook?> = _currentAudioBook.asStateFlow()

    private val _currentPosition = MutableStateFlow(0L)
    val currentPosition: StateFlow<Long> = _currentPosition.asStateFlow()

    private val _duration = MutableStateFlow(0L)
    val duration: StateFlow<Long> = _duration.asStateFlow()

    fun loadAudioBooks(books: List<AudioBook>) {
        medias.clear()
        books.forEach {
            medias.add(Pair(it, getAsMedia(it)))
        }
        exoPlayer.setMediaItems(medias.map { it.second }, false)
        exoPlayer.prepare()
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

    fun play(audioBook: AudioBook) {
        val index = medias.map { it.first }.indexOf(audioBook)
        exoPlayer.seekTo(index, 0L)
        if (!isPlaying.value) {
            exoPlayer.play()
        }
    }

    fun seekTo(position: Long) {
        exoPlayer.seekTo(position)
    }

    private fun getAsMedia(audioBook: AudioBook): MediaItem {
        val downloadRequest = audioDownloadManager.getDownload(audioBook.id)?.request
        return downloadRequest?.toMediaItem() ?: audioBook.toMediaItem()
    }

    private fun AudioBook.toMediaItem(): MediaItem {
        val metadata = MediaMetadata.Builder()
            .setTitle(name)
            .build()
        return MediaItem.Builder()
            .setMediaId(id)
            .setMediaMetadata(metadata)
            .setUri(audioUrl)
            .build()
    }

    fun removeDownload(audioBook: AudioBook) {
        downloadTracker.removeDownload(audioBook.id)
    }

    fun download(audioBook: AudioBook) {
        downloadTracker.startDownload(audioBook.toMediaItem())
    }
}
