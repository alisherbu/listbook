package kaa.alisherbu.listbook.feature.main.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import kaa.alisherbu.listbook.feature.home.component.HomeComponent
import kaa.alisherbu.listbook.feature.profile.component.ProfileComponent

interface MainComponent {

    val screenStack: Value<ChildStack<*, ChildScreen>>

    fun onHomeClicked()
    fun onProfileClicked()
    sealed interface ChildScreen {
        class Home(val component: HomeComponent) : ChildScreen
        class Profile(val component: ProfileComponent) : ChildScreen
    }

    sealed interface Output

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            output: (Output) -> Unit
        ): MainComponent
    }
}