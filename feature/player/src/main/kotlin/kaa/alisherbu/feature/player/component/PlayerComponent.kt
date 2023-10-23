package kaa.alisherbu.feature.player.component

import com.arkivanov.decompose.ComponentContext
import kaa.alisherbu.feature.player.store.PlayerState
import kotlinx.coroutines.flow.StateFlow

interface PlayerComponent {
    val state: StateFlow<PlayerState>

    fun interface Factory {
        operator fun invoke(componentContext: ComponentContext): PlayerComponent
    }
}