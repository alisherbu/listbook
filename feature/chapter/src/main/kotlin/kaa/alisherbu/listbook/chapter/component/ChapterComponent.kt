package kaa.alisherbu.listbook.chapter.component

import com.arkivanov.decompose.ComponentContext
import kaa.alisherbu.listbook.chapter.store.ChapterState
import kaa.alisherbu.listbook.core.shared.model.AudioBook
import kaa.alisherbu.listbook.core.shared.model.Chapter
import kotlinx.coroutines.flow.StateFlow

interface ChapterComponent {
    val state: StateFlow<ChapterState>

    fun onChapterClicked(chapter: Chapter)

    interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            audioBook: AudioBook
        ): ChapterComponent
    }
}