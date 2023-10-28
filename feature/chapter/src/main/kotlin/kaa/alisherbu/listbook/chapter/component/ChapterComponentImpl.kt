package kaa.alisherbu.listbook.chapter.component

import com.arkivanov.decompose.ComponentContext
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class ChapterComponentImpl @AssistedInject constructor(
    @Assisted componentContext: ComponentContext
) : ChapterComponent, ComponentContext by componentContext {

    @AssistedFactory
    interface Factory : ChapterComponent.Factory {
        override fun invoke(componentContext: ComponentContext): ChapterComponentImpl
    }
}