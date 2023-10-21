package kaa.alisherbu.listbook.feature.home.component

import com.arkivanov.decompose.ComponentContext
import kaa.alisherbu.listbook.feature.home.store.HomeState
import kotlinx.coroutines.flow.StateFlow

interface HomeComponent {

    val state: StateFlow<HomeState>

    fun interface Factory {
        operator fun invoke(componentContext: ComponentContext): HomeComponent
    }
}