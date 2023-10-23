package kaa.alisherbu.listbook.feature.home.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kaa.alisherbu.listbook.core.shared.model.AudioBook
import kaa.alisherbu.listbook.feature.home.store.HomeState
import kaa.alisherbu.listbook.feature.home.store.HomeStore
import kaa.alisherbu.listbook.feature.home.component.HomeComponent.Output
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Provider

class HomeComponentImpl @AssistedInject internal constructor(
    @Assisted componentContext: ComponentContext,
    @Assisted private val output: (Output) -> Unit,
    private val storeProvider: Provider<HomeStore>,
) : HomeComponent, ComponentContext by componentContext {
    private val store = instanceKeeper.getStore(storeProvider::get)

    override val state: StateFlow<HomeState> = store.stateFlow

    override fun onAudioBookClick(audioBook: AudioBook) {
        output(Output.AudioBookItemClick(audioBook))
    }

    @AssistedFactory
    interface Factory : HomeComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            output: (Output) -> Unit
        ): HomeComponentImpl
    }
}