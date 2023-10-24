package kaa.alisherbu.listbook.feature.main.component

import android.os.Parcelable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kaa.alisherbu.listbook.feature.home.component.HomeComponent
import kaa.alisherbu.listbook.feature.main.component.MainComponent.ChildScreen
import kaa.alisherbu.listbook.feature.main.component.MainComponent.Output
import kaa.alisherbu.listbook.feature.main.store.Intent
import kaa.alisherbu.listbook.feature.main.store.MainState
import kaa.alisherbu.listbook.feature.main.store.MainStore
import kaa.alisherbu.listbook.feature.profile.component.ProfileComponent
import kotlinx.coroutines.flow.StateFlow
import kotlinx.parcelize.Parcelize
import javax.inject.Provider

class MainComponentImpl @AssistedInject internal constructor(
    @Assisted componentContext: ComponentContext,
    @Assisted private val output: (Output) -> Unit,
    private val homeFactory: HomeComponent.Factory,
    private val profileFactory: ProfileComponent.Factory,
    private val storeProvider: Provider<MainStore>
) : MainComponent, ComponentContext by componentContext {
    private val store = instanceKeeper.getStore(storeProvider::get)

    private val screenNavigation = StackNavigation<ScreenConfig>()

    override val screenStack: Value<ChildStack<*, ChildScreen>> = childStack(
        screenNavigation,
        initialConfiguration = ScreenConfig.Home,
        handleBackButton = false,
        childFactory = ::createChildScreen
    )

    override val state: StateFlow<MainState> = store.stateFlow


    private fun createChildScreen(
        config: ScreenConfig,
        componentContext: ComponentContext
    ): ChildScreen {
        return when (config) {
            ScreenConfig.Home -> {
                ChildScreen.Home(homeFactory(componentContext, ::onHomeOutput))
            }

            ScreenConfig.Profile -> {
                ChildScreen.Profile(profileFactory(componentContext))
            }
        }
    }

    override fun onHomeClicked() {
        screenNavigation.bringToFront(ScreenConfig.Home)
    }

    override fun onProfileClicked() {
        screenNavigation.bringToFront(ScreenConfig.Profile)
    }

    override fun onPlayOrPause() {
        store.accept(Intent.PlayOrPause)
    }

    override fun onPlayerClicked() {
        output(Output.OpenPlayer)
    }

    private fun onHomeOutput(output: HomeComponent.Output) = when (output) {
        is HomeComponent.Output.AudioBookItemClick -> {
            store.accept(Intent.Play(output.audioBook))
        }
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
            output: (Output) -> Unit
        ): MainComponentImpl
    }
}