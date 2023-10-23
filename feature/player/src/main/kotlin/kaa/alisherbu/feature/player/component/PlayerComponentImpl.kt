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
    @Assisted private val audioBook: AudioBook,
    private val storeProvider: Provider<PlayerStore>
) : PlayerComponent, ComponentContext by componentContext {
    private val store = instanceKeeper.getStore(storeProvider::get)

    override val state: StateFlow<PlayerState> = store.stateFlow

    init {
        store.accept(Intent.Initialize(audioBook))
    }

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

    }

    override fun onUserPositionChangeFinished() {

    }


    @AssistedFactory
    interface Factory : PlayerComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            output: (Output) -> Unit,
            audioBook: AudioBook
        ): PlayerComponentImpl
    }
}