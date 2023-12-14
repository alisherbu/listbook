package kaa.alisherbu.listbook.chapter.store

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kaa.alisherbu.listbook.chapter.domain.usecase.GetChaptersByBookIdUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class ChapterExecutor @Inject constructor(
    private val getChapters: GetChaptersByBookIdUseCase
) : CoroutineExecutor<Intent, Action, ChapterState, Message, Label>() {

    override fun executeIntent(intent: Intent, getState: () -> ChapterState) {
        when (intent) {
            is Intent.GetChapters -> scope.launch {
                val chapters = getChapters(intent.audioBook.id)
                dispatch(Message.UpdateChapters(chapters))
            }
        }
    }
}
