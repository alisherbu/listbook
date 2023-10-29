package kaa.alisherbu.listbook.chapter.store

import com.arkivanov.mvikotlin.core.store.Store
import kaa.alisherbu.listbook.core.shared.model.AudioBook
import kaa.alisherbu.listbook.core.shared.model.Chapter

internal sealed interface Intent {
    class GetChapters(val audioBook: AudioBook) : Intent
}

internal sealed interface Action

internal sealed interface Label
internal sealed interface Message {
    class UpdateChapters(val chapters: List<Chapter>) : Message
}

internal interface ChapterStore : Store<Intent, ChapterState, Label>