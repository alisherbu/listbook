package kaa.alisherbu.listbook.feature.home.component

import com.arkivanov.decompose.ComponentContext
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class HomeComponentImpl @AssistedInject constructor(
    @Assisted componentContext: ComponentContext
) : HomeComponent, ComponentContext by componentContext {

    @AssistedFactory
    interface Factory : HomeComponent.Factory {
        override fun invoke(componentContext: ComponentContext): HomeComponentImpl
    }
}