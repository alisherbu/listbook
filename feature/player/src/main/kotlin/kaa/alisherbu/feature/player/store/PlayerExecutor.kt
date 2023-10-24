package kaa.alisherbu.feature.player.store

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kaa.alisherbu.listbook.core.shared.player.AudioPlayer
import javax.inject.Inject

internal class PlayerExecutor @Inject constructor(
    private val audioPlayer: AudioPlayer
) : CoroutineExecutor<Intent, Action, PlayerState, Message, Label>() {
    override fun executeIntent(intent: Intent, getState: () -> PlayerState) {
        when (intent) {
            is Intent.Initialize -> {
                dispatch(Message.SetAudioBook(title = intent.audioBook.name))
            }

            Intent.PlayOrPause -> {
                audioPlayer.playOrPause()
            }

            Intent.SkipToNextAudio -> {
                audioPlayer.next()
            }

            Intent.SkipToPreviousAudio -> {
                audioPlayer.previous()
            }
        }
    }
}