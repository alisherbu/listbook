package kaa.alisherbu.listbook.core.shared.player

import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kaa.alisherbu.listbook.core.shared.model.AudioBook
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

class AudioPlayer(private val exoPlayer: ExoPlayer) {
    private var isPlaying = false
    private val medias: MutableMap<AudioBook, MediaItem> = mutableMapOf()

    init {
        exoPlayer.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                _playing.trySend(isPlaying)
            }
        })
    }

    private val _playing = Channel<Boolean>()
    val playing: Flow<Boolean> = _playing.receiveAsFlow()
    fun loadAudioBooks(books: List<AudioBook>) {
        books.forEach { medias[it] = MediaItem.fromUri(Uri.parse(it.audioUrl)) }
        exoPlayer.setMediaItems(medias.values.toList())
        exoPlayer.prepare()
    }

    fun playOrPause() {
        if (isPlaying) exoPlayer.pause()
        else exoPlayer.play()
        isPlaying = !isPlaying
    }

    fun previous() {
        exoPlayer.seekToPrevious()
    }

    fun next() {
        exoPlayer.seekToNext()
    }

    fun play(audioBook: AudioBook) {
        val index = medias.keys.indexOf(audioBook)
        exoPlayer.seekTo(index, 0L)
    }

}