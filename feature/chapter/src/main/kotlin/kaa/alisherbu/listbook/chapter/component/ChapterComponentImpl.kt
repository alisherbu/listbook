package kaa.alisherbu.listbook.chapter.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kaa.alisherbu.listbook.chapter.store.ChapterState
import kaa.alisherbu.listbook.chapter.store.ChapterStore
import kaa.alisherbu.listbook.core.shared.model.Chapter
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Provider

class ChapterComponentImpl @AssistedInject internal constructor(
    @Assisted componentContext: ComponentContext,
    private val storeProvider: Provider<ChapterStore>
) : ChapterComponent, ComponentContext by componentContext {
    private val store = instanceKeeper.getStore(storeProvider::get)

    override val state: StateFlow<ChapterState> = store.stateFlow

    override fun onChapterClicked(chapter: Chapter) {

    }
    @AssistedFactory
    interface Factory : ChapterComponent.Factory {
        override fun invoke(componentContext: ComponentContext): ChapterComponentImpl
    }
}