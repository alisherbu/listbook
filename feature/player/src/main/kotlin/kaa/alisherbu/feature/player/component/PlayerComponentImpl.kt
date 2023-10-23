package kaa.alisherbu.feature.player.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kaa.alisherbu.feature.player.store.PlayerState
import kaa.alisherbu.feature.player.store.PlayerStore
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Provider

class PlayerComponentImpl @AssistedInject internal constructor(
    @Assisted componentContext: ComponentContext,
    private val storeProvider: Provider<PlayerStore>
) : PlayerComponent, ComponentContext by componentContext {
    private val store = instanceKeeper.getStore(storeProvider::get)

    override val state: StateFlow<PlayerState> = store.stateFlow

    @AssistedFactory
    interface Factory : PlayerComponent.Factory {
        override fun invoke(componentContext: ComponentContext): PlayerComponentImpl
    }
}