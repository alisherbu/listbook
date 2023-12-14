package kaa.alisherbu.listbook.feature.home.store

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kaa.alisherbu.listbook.domain.usecase.GetAudioBooksUseCase
import kaa.alisherbu.listbook.domain.usecase.GetCategoriesUseCase
import kaa.alisherbu.listbook.domain.usecase.RefreshUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class HomeExecutor @Inject constructor(
    private val getAudioBooks: GetAudioBooksUseCase,
    private val refresh: RefreshUseCase,
    private val getCategories: GetCategoriesUseCase
) : CoroutineExecutor<Intent, Action, HomeState, Message, Label>() {

    override fun executeAction(action: Action, getState: () -> HomeState) {
        when (action) {
            Action.Init -> init()
        }
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

    private fun init() {
        getCategories().onEach {
            dispatch(Message.UpdateCategories(it))
        }.launchIn(scope)

        getAudioBooks().onEach {
            dispatch(Message.UpdateAudioBooks(it))
        }.launchIn(scope)
    }
}
