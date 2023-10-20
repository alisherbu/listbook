package kaa.alisherbu.listbook.feature.main.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import kaa.alisherbu.listbook.feature.home.component.HomeComponent

interface MainComponent {

    val screenStack: Value<ChildStack<*, ChildScreen>>

    sealed interface ChildScreen {
        class Home(val component: HomeComponent) : ChildScreen
        class Profile : ChildScreen
    }

    sealed interface Output

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            output: (Output) -> Unit
        ): MainComponent
    }
}