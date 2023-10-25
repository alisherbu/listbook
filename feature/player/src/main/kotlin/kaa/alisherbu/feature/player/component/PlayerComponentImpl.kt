package kaa.alisherbu.feature.player.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kaa.alisherbu.feature.player.store.PlayerState
import kaa.alisherbu.feature.player.store.PlayerStore
import kaa.alisherbu.feature.player.component.PlayerComponent.Output
import kaa.alisherbu.feature.player.store.Intent
import kaa.alisherbu.listbook.core.shared.model.AudioBook
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Provider

class PlayerComponentImpl @AssistedInject internal constructor(
    @Assisted componentContext: ComponentContext,
    @Assisted private val output: (Output) -> Unit,
    private val storeProvider: Provider<PlayerStore>
) : PlayerComponent, ComponentContext by componentContext {
    private val store = instanceKeeper.getStore(storeProvider::get)

    override val state: StateFlow<PlayerState> = store.stateFlow

    override fun onBackClicked() {
        output(Output.Back)
    }

    override fun onPreviousAudio() {
        store.accept(Intent.SkipToPreviousAudio)
    }

    override fun onPlayPauseAudio() {
        store.accept(Intent.PlayOrPause)
    }

    override fun onNextAudio() {
        store.accept(Intent.SkipToNextAudio)
    }

    override fun onUserPositionChange(value: Long) {
        store.accept(Intent.ChangeUserPosition(value))
    }

    override fun onUserPositionChangeFinished() {
        store.accept(Intent.ChangeUserPositionFinished)
    }

    override fun onDownloadClick() {
        store.accept(Intent.Download)
    }


    @AssistedFactory
    interface Factory : PlayerComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            output: (Output) -> Unit
        ): PlayerComponentImpl
    }
}