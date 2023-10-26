package kaa.alisherbu.listbook.feature.home.component

import com.arkivanov.decompose.ComponentContext
import kaa.alisherbu.listbook.core.shared.model.AudioBook
import kaa.alisherbu.listbook.feature.home.store.HomeState
import kotlinx.coroutines.flow.StateFlow

interface HomeComponent {

    val state: StateFlow<HomeState>

    fun onAudioBookClick(audioBook: AudioBook)

    sealed interface Output {
        class AudioBookItemClick(val audioBook: AudioBook) : Output
    }

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            output: (Output) -> Unit,
        ): HomeComponent
    }
}
