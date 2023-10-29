package kaa.alisherbu.listbook.feature.main.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import kaa.alisherbu.listbook.core.shared.model.AudioBook
import kaa.alisherbu.listbook.feature.home.component.HomeComponent
import kaa.alisherbu.listbook.feature.main.store.MainState
import kaa.alisherbu.listbook.feature.profile.component.ProfileComponent
import kotlinx.coroutines.flow.StateFlow

interface MainComponent {

    val screenStack: Value<ChildStack<*, ChildScreen>>

    val state: StateFlow<MainState>

    fun onHomeClicked()

    fun onProfileClicked()

    fun onPlayOrPause()

    fun onPlayerClicked(audioBook: AudioBook?)

    sealed interface ChildScreen {
        class Home(val component: HomeComponent) : ChildScreen
        class Profile(val component: ProfileComponent) : ChildScreen
    }

    sealed interface Output {
        class OpenPlayer(val audioBook: AudioBook) : Output
    }

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            output: (Output) -> Unit,
        ): MainComponent
    }
}
