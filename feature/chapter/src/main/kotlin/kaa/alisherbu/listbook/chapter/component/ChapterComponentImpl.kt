package kaa.alisherbu.listbook.chapter.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kaa.alisherbu.listbook.chapter.component.ChapterComponent.Output
import kaa.alisherbu.listbook.chapter.store.ChapterState
import kaa.alisherbu.listbook.chapter.store.ChapterStore
import kaa.alisherbu.listbook.chapter.store.Intent
import kaa.alisherbu.listbook.core.shared.model.AudioBook
import kaa.alisherbu.listbook.core.shared.model.Chapter
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Provider

class ChapterComponentImpl @AssistedInject internal constructor(
    @Assisted componentContext: ComponentContext,
    @Assisted audioBook: AudioBook,
    @Assisted private val output: (Output) -> Unit,
    private val storeProvider: Provider<ChapterStore>
) : ChapterComponent, ComponentContext by componentContext {
    private val store = instanceKeeper.getStore(storeProvider::get)

    init {
        store.accept(Intent.GetChapters(audioBook))
    }

    override val state: StateFlow<ChapterState> = store.stateFlow

    override fun onChapterClicked(chapter: Chapter) {
        output(Output.Play(chapter))
    }

    @AssistedFactory
    interface Factory : ChapterComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            audioBook: AudioBook,
            output: (Output) -> Unit
        ): ChapterComponentImpl
    }
}
