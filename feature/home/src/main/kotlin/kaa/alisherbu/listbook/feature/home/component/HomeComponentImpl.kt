package kaa.alisherbu.listbook.feature.home.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.arkivanov.mvikotlin.extensions.coroutines.states
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kaa.alisherbu.listbook.feature.home.store.HomeState
import kaa.alisherbu.listbook.feature.home.store.HomeStore
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Provider

class HomeComponentImpl @AssistedInject internal constructor(
    @Assisted componentContext: ComponentContext,
    private val storeProvider: Provider<HomeStore>
) : HomeComponent, ComponentContext by componentContext {
    private val store = instanceKeeper.getStore(storeProvider::get)

    override val state: StateFlow<HomeState> = store.stateFlow

    @AssistedFactory
    interface Factory : HomeComponent.Factory {
        override fun invoke(componentContext: ComponentContext): HomeComponentImpl
    }
}