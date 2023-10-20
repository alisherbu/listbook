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
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kaa.alisherbu.listbook.feature.auth.component.AuthComponent
import kaa.alisherbu.listbook.feature.dialog.component.MessageDialogComponent
import kaa.alisherbu.listbook.feature.sign_in.component.SignInComponent
import kaa.alisherbu.listbook.feature.signup.component.SignupComponent
import kaa.alisherbu.listbook.feature.root.component.RootComponent.ChildScreen
import kaa.alisherbu.listbook.feature.root.component.RootComponent.ChildDialog
import kotlinx.parcelize.Parcelize

class RootComponentImpl @AssistedInject internal constructor(
    @Assisted componentContext: ComponentContext,
    private val authFactory: AuthComponent.Factory,
    private val signInFactory: SignInComponent.Factory,
    private val signupFactory: SignupComponent.Factory
) : RootComponent, ComponentContext by componentContext {

    private val screenNavigation = StackNavigation<ScreenConfig>()
    private val dialogNavigation = SlotNavigation<DialogConfig>()

    override val screenStack: Value<ChildStack<*, ChildScreen>> = childStack(
        source = screenNavigation,
        initialConfiguration = ScreenConfig.Auth,
        handleBackButton = true,
        childFactory = ::createChildScreen
    )

    override val dialogSlot: Value<ChildSlot<*, ChildDialog>> = childSlot(
        source = dialogNavigation,
        handleBackButton = true,
        childFactory = ::createChildDialog
    )


    private fun createChildScreen(
        configuration: ScreenConfig,
        componentContext: ComponentContext,
    ): ChildScreen = when (configuration) {
        ScreenConfig.Auth -> {
            ChildScreen.Auth(authFactory(componentContext, ::onAuthOutput))
        }

        ScreenConfig.Home -> {
            ChildScreen.Home("")
        }

        ScreenConfig.Signup -> {
            ChildScreen.Signup(signupFactory(componentContext, ::onSignupOutput))
        }

        ScreenConfig.SignIn -> {
            ChildScreen.SignIn(signInFactory(componentContext, ::onSignInOutput))
        }
    }

    private fun createChildDialog(
        config: DialogConfig,
        componentContext: ComponentContext
    ): ChildDialog = when (config) {
        is DialogConfig.Message -> {
            ChildDialog.Message(
                MessageDialogComponent(
                    componentContext,
                    config.text,
                    onDismissed = dialogNavigation::dismiss
                )
            )
        }
    }

    private fun onAuthOutput(output: AuthComponent.Output) {
        when (output) {
            AuthComponent.Output.Signup -> {
                screenNavigation.push(ScreenConfig.Signup)
            }

            AuthComponent.Output.SignIn -> {
                screenNavigation.push(ScreenConfig.SignIn)
            }
        }
    }

    private fun onSignupOutput(output: SignupComponent.Output) {
        when (output) {
            SignupComponent.Output.Back -> {
                screenNavigation.pop()
            }

            is SignupComponent.Output.Error -> {
                dialogNavigation.activate(DialogConfig.Message(output.message))
            }
        }
    }

    private fun onSignInOutput(output: SignInComponent.Output) {
        when (output) {
            SignInComponent.Output.Back -> {
                screenNavigation.pop()
            }

            SignInComponent.Output.Home -> {
                screenNavigation.push(ScreenConfig.Home)
            }

            is SignInComponent.Output.Error -> {
                dialogNavigation.activate(DialogConfig.Message(output.message))
            }
        }
    }

    private sealed interface ScreenConfig : Parcelable {
        @Parcelize
        object Auth : ScreenConfig

        @Parcelize
        object Home : ScreenConfig

        @Parcelize
        object Signup : ScreenConfig

        @Parcelize
        object SignIn : ScreenConfig
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
            componentContext: ComponentContext
        ): RootComponentImpl
    }
}
