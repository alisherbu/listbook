package kaa.alisherbu.listbook.feature.root.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kaa.alisherbu.feature.player.component.PlayerComponent
import kaa.alisherbu.listbook.core.shared.coroutine.AppDispatchers
import kaa.alisherbu.listbook.feature.auth.component.AuthComponent
import kaa.alisherbu.listbook.feature.dialog.component.MessageDialogComponent
import kaa.alisherbu.listbook.feature.main.component.MainComponent
import kaa.alisherbu.listbook.feature.root.component.RootComponent.ChildDialog
import kaa.alisherbu.listbook.feature.root.component.RootComponent.ChildScreen
import kaa.alisherbu.listbook.feature.root.store.Label
import kaa.alisherbu.listbook.feature.root.store.RootStore
import kaa.alisherbu.listbook.feature.sign_in.component.SignInComponent
import kaa.alisherbu.listbook.feature.signup.component.SignupComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import javax.inject.Provider
import kotlin.time.Duration.Companion.milliseconds

class RootComponentImpl @AssistedInject internal constructor(
    @Assisted componentContext: ComponentContext,
    private val authFactory: AuthComponent.Factory,
    private val signInFactory: SignInComponent.Factory,
    private val signupFactory: SignupComponent.Factory,
    private val mainFactory: MainComponent.Factory,
    private val playerFactory: PlayerComponent.Factory,
    private val storeProvider: Provider<RootStore>,
    dispatchers: AppDispatchers,
) : RootComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore(storeProvider::get)
    private val mainScope = CoroutineScope(dispatchers.main)
    private val screenNavigation = StackNavigation<ScreenConfig>()
    private val dialogNavigation = SlotNavigation<DialogConfig>()

    init {
        store.labels
            .onEach(::handleLabel)
            .launchIn(mainScope)
        mainScope.launch {
            delay(INIT_DELAY)
            store.init()
        }
        lifecycle.doOnDestroy(mainScope::cancel)
    }

    override val screenStack: Value<ChildStack<*, ChildScreen>> = childStack(
        source = screenNavigation,
        initialConfiguration = ScreenConfig.Undefined,
        handleBackButton = true,
        childFactory = ::createChildScreen,
    )

    override val dialogSlot: Value<ChildSlot<*, ChildDialog>> = childSlot(
        source = dialogNavigation,
        handleBackButton = true,
        childFactory = ::createChildDialog,
    )

    private fun handleLabel(label: Label) {
        when (label) {
            Label.UserAlreadySigned -> screenNavigation.replaceCurrent(ScreenConfig.Main)
            Label.UserNotSigned -> screenNavigation.replaceCurrent(ScreenConfig.Auth)
        }
    }

    private fun createChildScreen(
        config: ScreenConfig,
        componentContext: ComponentContext,
    ): ChildScreen = when (config) {
        ScreenConfig.Auth -> {
            ChildScreen.Auth(authFactory(componentContext, ::onAuthOutput))
        }

        ScreenConfig.Main -> {
            ChildScreen.Main(mainFactory(componentContext, ::onMainOutput))
        }

        ScreenConfig.Signup -> {
            ChildScreen.Signup(signupFactory(componentContext, ::onSignupOutput))
        }

        ScreenConfig.SignIn -> {
            ChildScreen.SignIn(signInFactory(componentContext, ::onSignInOutput))
        }

        is ScreenConfig.Player -> {
            ChildScreen.Player(playerFactory(componentContext, ::onPlayerOutput))
        }

        ScreenConfig.Undefined -> {
            ChildScreen.Undefined
        }
    }

    private fun createChildDialog(
        config: DialogConfig,
        componentContext: ComponentContext,
    ): ChildDialog = when (config) {
        is DialogConfig.Message -> {
            ChildDialog.Message(
                MessageDialogComponent(
                    componentContext,
                    config.text,
                    onDismissed = dialogNavigation::dismiss,
                ),
            )
        }
    }

    private fun onAuthOutput(output: AuthComponent.Output) = when (output) {
        AuthComponent.Output.Signup -> {
            screenNavigation.push(ScreenConfig.Signup)
        }

        AuthComponent.Output.SignIn -> {
            screenNavigation.push(ScreenConfig.SignIn)
        }
    }

    private fun onSignupOutput(output: SignupComponent.Output) = when (output) {
        SignupComponent.Output.Back -> {
            screenNavigation.pop()
        }

        is SignupComponent.Output.Error -> {
            dialogNavigation.activate(DialogConfig.Message(output.message))
        }
    }

    private fun onSignInOutput(output: SignInComponent.Output) = when (output) {
        SignInComponent.Output.Back -> {
            screenNavigation.pop()
        }

        SignInComponent.Output.Main -> {
            screenNavigation.replaceAll(ScreenConfig.Main)
        }

        is SignInComponent.Output.Error -> {
            dialogNavigation.activate(DialogConfig.Message(output.message))
        }
    }

    private fun onMainOutput(output: MainComponent.Output) = when (output) {
        is MainComponent.Output.OpenPlayerWithBook -> {
            screenNavigation.push(ScreenConfig.Player)
        }

        MainComponent.Output.OpenPlayer -> {
            screenNavigation.push(ScreenConfig.Player)
        }
    }

    private fun onPlayerOutput(output: PlayerComponent.Output) {
        when (output) {
            PlayerComponent.Output.Back -> screenNavigation.pop()
        }
    }

    private sealed interface ScreenConfig : Parcelable {
        @Parcelize
        object Auth : ScreenConfig

        @Parcelize
        object Main : ScreenConfig

        @Parcelize
        object Signup : ScreenConfig

        @Parcelize
        object SignIn : ScreenConfig

        @Parcelize
        object Undefined : ScreenConfig

        @Parcelize
        object Player : ScreenConfig
    }

    private sealed interface DialogConfig : Parcelable {
        @Parcelize
        class Message(
            val text: String,
        ) : DialogConfig
    }

    @AssistedFactory
    interface Factory : RootComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
        ): RootComponentImpl
    }

    companion object {
        private val INIT_DELAY = 1.milliseconds
    }
}
