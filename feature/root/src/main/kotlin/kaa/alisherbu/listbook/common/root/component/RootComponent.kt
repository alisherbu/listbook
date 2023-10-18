package kaa.alisherbu.listbook.common.root.component

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
import com.arkivanov.mvikotlin.core.store.StoreFactory
import kaa.alisherbu.listbook.common.auth.component.AuthComponent
import kaa.alisherbu.listbook.common.dialog.component.MessageDialogComponent
import kaa.alisherbu.listbook.common.sign_in.component.SignInComponent
import kaa.alisherbu.listbook.common.signup.component.SignupComponent
import kotlinx.parcelize.Parcelize

class RootComponent(
    componentContext: ComponentContext,
    private val authComponent: (ComponentContext, (AuthComponent.Output) -> Unit) -> AuthComponent,
    private val signupComponent: (ComponentContext, (SignupComponent.Output) -> Unit) -> SignupComponent,
    private val signInComponent: (ComponentContext, (SignInComponent.Output) -> Unit) -> SignInComponent,
) : ComponentContext by componentContext {

    constructor(
        componentContext: ComponentContext,
        storeFactory: StoreFactory,
    ) : this(
        componentContext,
        authComponent = { childContext, output ->
            AuthComponent(childContext, storeFactory, output)
        },
        signupComponent = { childContext, output ->
            SignupComponent(childContext, storeFactory, output)
        },
        signInComponent = { childContext, output ->
            SignInComponent(childContext, storeFactory, output)
        }
    )

    private val screenNavigation = StackNavigation<ScreenConfig>()
    private val dialogNavigation = SlotNavigation<DialogConfig>()

    internal val screenStack: Value<ChildStack<*, ChildScreen>> = childStack(
        source = screenNavigation,
        initialConfiguration = ScreenConfig.Auth,
        handleBackButton = true,
        childFactory = ::createChildScreen
    )

    internal val dialogSlot: Value<ChildSlot<*, ChildDialog>> = childSlot(
        source = dialogNavigation,
        handleBackButton = true,
        childFactory = ::createChildDialog
    )


    private fun createChildScreen(
        configuration: ScreenConfig,
        componentContext: ComponentContext,
    ): ChildScreen = when (configuration) {
        ScreenConfig.Auth -> {
            ChildScreen.Auth(authComponent(componentContext, ::onAuthOutput))
        }

        ScreenConfig.Home -> {
            ChildScreen.Home("")
        }

        ScreenConfig.Signup -> {
            ChildScreen.Signup(signupComponent(componentContext, ::onSignupOutput))
        }

        ScreenConfig.SignIn -> {
            ChildScreen.SignIn(signInComponent(componentContext, ::onSignInOutput))
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

    internal sealed interface ChildScreen {
        class Auth(val component: AuthComponent) : ChildScreen

        data class Home(val text: String) : ChildScreen
        class Signup(val component: SignupComponent) : ChildScreen

        class SignIn(val component: SignInComponent) : ChildScreen
    }

    internal sealed interface ChildDialog {
        class Message(val component: MessageDialogComponent) : ChildDialog
    }

    private sealed interface DialogConfig : Parcelable {
        @Parcelize
        class Message(
            val text: String,
        ) : DialogConfig
    }
}
