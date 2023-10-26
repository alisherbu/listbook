package kaa.alisherbu.feature.player.component

import com.arkivanov.decompose.ComponentContext
import kaa.alisherbu.feature.player.store.PlayerState
import kotlinx.coroutines.flow.StateFlow

interface PlayerComponent {
    val state: StateFlow<PlayerState>

    fun onBackClicked()

    fun onPreviousAudio()
    fun onPlayPauseAudio()
    fun onNextAudio()
    fun onUserPositionChange(value: Long)
    fun onUserPositionChangeFinished()
    fun onDownloadClick()
    fun onRemoveClick()

    sealed interface Output {
        object Back : Output
    }

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            output: (Output) -> Unit,
        ): PlayerComponent
    }
}
