package kaa.alisherbu.listbook.chapter.store

import com.arkivanov.mvikotlin.core.store.Reducer
import javax.inject.Inject

internal class ChapterReducer @Inject constructor() : Reducer<ChapterState, Message> {
    override fun ChapterState.reduce(msg: Message): ChapterState {
        return when (msg) {
            is Message.UpdateChapters -> {
                copy(chapters = msg.chapters)
            }
        }
    }
}