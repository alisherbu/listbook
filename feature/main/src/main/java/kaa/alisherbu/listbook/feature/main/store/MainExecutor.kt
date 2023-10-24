package kaa.alisherbu.listbook.feature.main.store

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kaa.alisherbu.listbook.core.shared.player.AudioPlayer
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

internal class MainExecutor @Inject constructor(
    private val audioPlayer: AudioPlayer
) : CoroutineExecutor<Intent, Action, MainState, Message, Label>() {

    init {
        audioPlayer.playing.onEach {
            dispatch(Message.PlayOrPause(it))
        }.launchIn(scope)
    }

    override fun executeIntent(intent: Intent, getState: () -> MainState) {
        when (intent) {
            is Intent.Play -> {
                audioPlayer.play(intent.audioBook)
            }

            Intent.PlayOrPause -> {
                audioPlayer.playOrPause()
            }
        }
    }
}