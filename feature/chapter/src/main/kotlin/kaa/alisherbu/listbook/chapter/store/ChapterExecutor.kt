package kaa.alisherbu.listbook.chapter.store

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kaa.alisherbu.listbook.domain.usecase.GetChaptersUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

internal class ChapterExecutor @Inject constructor(
    private val getChapters: GetChaptersUseCase
) : CoroutineExecutor<Intent, Action, ChapterState, Message, Label>() {
    init {
        getChapters().onEach {
            dispatch(Message.UpdateChapters(it))
        }.launchIn(scope)
    }
}