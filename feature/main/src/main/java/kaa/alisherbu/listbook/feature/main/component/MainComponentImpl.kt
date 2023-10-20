package kaa.alisherbu.listbook.feature.main.component

import android.os.Parcelable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.parcelize.Parcelize

class MainComponentImpl @AssistedInject constructor(
    @Assisted componentContext: ComponentContext,
    @Assisted private val output: (MainComponent.Output) -> Unit
) : MainComponent, ComponentContext by componentContext {

    private val screenNavigation = StackNavigation<ScreenConfig>()

    override val screenStack: Value<ChildStack<*, MainComponent.ChildScreen>> = childStack(
        screenNavigation,
        initialConfiguration = ScreenConfig.Home,
        handleBackButton = false,
        childFactory = ::createChildScreen
    )


    private fun createChildScreen(
        config: ScreenConfig,
        componentContext: ComponentContext
    ): MainComponent.ChildScreen {
        error("")
    }

    private sealed interface ScreenConfig : Parcelable {
        @Parcelize
        object Home : ScreenConfig

        @Parcelize
        object Profile : ScreenConfig
    }

    @AssistedFactory
    interface Factory : MainComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            output: (MainComponent.Output) -> Unit
        ): MainComponentImpl
    }
}