package kaa.alisherbu.listbook.feature.home.store

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kaa.alisherbu.listbook.domain.usecase.GetAudioBooksUseCase
import kaa.alisherbu.service.player.AudioPlayer
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

internal class HomeExecutor @Inject constructor(
    private val getAudioBooks: GetAudioBooksUseCase,
    private val audioPlayer: AudioPlayer,
) : CoroutineExecutor<Intent, Action, HomeState, Message, Label>() {
    override fun executeAction(action: Action, getState: () -> HomeState) {
        when (action) {
            Action.LoadAudioBooks -> getAudioBooks().onEach {
                dispatch(Message.UpdateAudioBooks(it))
                audioPlayer.loadAudioBooks(it)
            }.launchIn(scope)
        }
    }
}
