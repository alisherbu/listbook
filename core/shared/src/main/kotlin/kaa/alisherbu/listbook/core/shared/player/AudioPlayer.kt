package kaa.alisherbu.listbook.core.shared.player

import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kaa.alisherbu.listbook.core.shared.model.AudioBook
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AudioPlayer(private val exoPlayer: ExoPlayer) {
    private val medias: MutableList<Pair<AudioBook, MediaItem>> = mutableListOf()

    init {
        exoPlayer.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                _isPlaying.tryEmit(isPlaying)
            }

            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                medias.find { it.second == mediaItem }?.first?.let(_currentAudioBook::tryEmit)
            }
        })
    }

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _currentAudioBook = MutableStateFlow<AudioBook?>(null)
    val currentAudioBook: StateFlow<AudioBook?> = _currentAudioBook.asStateFlow()

    fun loadAudioBooks(books: List<AudioBook>) {
        books.forEach { medias.add(Pair(it, MediaItem.fromUri(Uri.parse(it.audioUrl)))) }
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
}