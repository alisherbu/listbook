package kaa.alisherbu.listbook.core.shared.player

import android.content.Context
import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.offline.DownloadRequest
import androidx.media3.exoplayer.offline.DownloadService
import kaa.alisherbu.listbook.core.shared.coroutine.AppDispatchers
import kaa.alisherbu.listbook.core.shared.model.AudioBook
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import kotlin.time.Duration.Companion.milliseconds

@UnstableApi
class AudioPlayer(
    private val exoPlayer: ExoPlayer,
    private val context: Context,
    dispatchers: AppDispatchers
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

            override fun onPositionDiscontinuity(
                oldPosition: Player.PositionInfo,
                newPosition: Player.PositionInfo,
                reason: Int
            ) {
                Timber.d("oldPosition=${oldPosition.positionMs}, newPosition=${newPosition.positionMs}")
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
        books.forEach { medias.add(Pair(it, it.toMediaItem())) }
        exoPlayer.setMediaItems(medias.map { it.second })
        exoPlayer.prepare()
    }

    fun playOrPause() {
        if (isPlaying.value) exoPlayer.pause()
        else exoPlayer.play()
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


    fun download(audioBook: AudioBook) {
        val downloadRequest =
            DownloadRequest.Builder(audioBook.id, Uri.parse(audioBook.audioUrl)).build()
        DownloadService.sendAddDownload(
            /* context = */ context,
            /* clazz = */ AudioDownloadService::class.java,
            /* downloadRequest = */ downloadRequest,
            /* foreground = */ true
        )
    }
}