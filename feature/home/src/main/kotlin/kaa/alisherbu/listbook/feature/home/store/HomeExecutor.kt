package kaa.alisherbu.listbook.feature.home.store

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kaa.alisherbu.listbook.domain.usecase.GetAudioBooksUseCase
import kaa.alisherbu.listbook.domain.usecase.GetChaptersUseCase
import kaa.alisherbu.listbook.feature.home.domain.usecase.RefreshUseCase
import kaa.alisherbu.service.player.AudioPlayer
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class HomeExecutor @Inject constructor(
    getAudioBooks: GetAudioBooksUseCase,
    private val refresh: RefreshUseCase,
    private val audioPlayer: AudioPlayer,
) : CoroutineExecutor<Intent, Action, HomeState, Message, Label>() {
    init {
        getAudioBooks().onEach {
            dispatch(Message.UpdateAudioBooks(it))
        }.launchIn(scope)
    }

    override fun executeIntent(intent: Intent, getState: () -> HomeState) {
        when (intent) {
            Intent.Refresh -> scope.launch {
                dispatch(Message.UpdateRefresh(isRefreshing = true))
                refresh()
                delay(1000)
                dispatch(Message.UpdateRefresh(isRefreshing = false))
            }
        }
    }
}
