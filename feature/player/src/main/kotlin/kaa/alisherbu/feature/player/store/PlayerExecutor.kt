package kaa.alisherbu.feature.player.store

import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kaa.alisherbu.listbook.core.shared.model.AudioBook
import javax.inject.Inject

internal class PlayerExecutor @Inject constructor(
    private val exoPlayer: ExoPlayer
) : CoroutineExecutor<Intent, Action, PlayerState, Message, Label>() {
    override fun executeIntent(intent: Intent, getState: () -> PlayerState) {
        when (intent) {
            is Intent.Initialize -> {
                dispatch(Message.SetAudioBook(title = intent.audioBook.name))
                initialize(intent.audioBook)
            }

            Intent.PlayOrPause -> {
                if (exoPlayer.isPlaying) {
                    exoPlayer.pause()
                    dispatch(Message.PlayOrPause(isPlaying = false))

                } else {
                    exoPlayer.play()
                    dispatch(Message.PlayOrPause(isPlaying = true))
                }
            }

            Intent.SkipToNextAudio -> {}
            Intent.SkipToPreviousAudio -> {}
        }
    }

    private fun initialize(audioBook: AudioBook) {
        val mediaItem = MediaItem.fromUri(Uri.parse(audioBook.audioUrl))
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.play()
    }
}