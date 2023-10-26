package kaa.alisherbu.listbook.feature.home.store

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kaa.alisherbu.listbook.core.shared.player.AudioPlayer
import kaa.alisherbu.listbook.feature.home.domain.usecase.LoadAudioBooksUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

internal class HomeExecutor @Inject constructor(
    private val loadAudioBooks: LoadAudioBooksUseCase,
    private val audioPlayer: AudioPlayer,
) : CoroutineExecutor<Intent, Action, HomeState, Message, Label>() {
    override fun executeAction(action: Action, getState: () -> HomeState) {
        when (action) {
            Action.LoadAudioBooks -> loadAudioBooks().onEach {
                dispatch(Message.UpdateAudioBooks(it))
                audioPlayer.loadAudioBooks(it)
            }.launchIn(scope)
        }
    }
}
