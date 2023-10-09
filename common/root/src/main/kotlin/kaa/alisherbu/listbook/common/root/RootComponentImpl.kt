package kaa.alisherbu.listbook.common.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.mvikotlin.core.store.StoreFactory
import kaa.alisherbu.listbook.common.auth.component.AuthComponent
import kaa.alisherbu.listbook.common.auth.component.AuthComponentImpl
import kaa.alisherbu.listbook.common.root.RootComponent.Child
import kaa.alisherbu.listbook.common.sign_in.component.SignInComponent
import kaa.alisherbu.listbook.common.sign_in.component.SignInComponentImpl
import kaa.alisherbu.listbook.common.signup.component.SignupComponent
import kaa.alisherbu.listbook.common.signup.component.SignupComponentImpl
import kotlinx.parcelize.Parcelize

class RootComponentImpl(
    componentContext: ComponentContext,
    private val authComponent: (ComponentContext, (AuthComponent.Output) -> Unit) -> AuthComponent,
    private val signupComponent: (ComponentContext, (SignupComponent.Output) -> Unit) -> SignupComponent,
    private val signInComponent: (ComponentContext, (SignInComponent.Output) -> Unit) -> SignInComponent,
) : RootComponent, ComponentContext by componentContext {

    constructor(
        componentContext: ComponentContext,
        storeFactory: StoreFactory,
    ) : this(
        componentContext,
        authComponent = { childContext, output ->
            AuthComponentImpl(childContext, storeFactory, output)
        },
        signupComponent = { childContext, output ->
            SignupComponentImpl(childContext, storeFactory, output)
        },
        signInComponent = { childContext, output ->
            SignInComponentImpl(childContext, storeFactory, output)
        }
    )

    private val stackNavigation = StackNavigation<ScreenConfig>()

    override val childStack: Value<ChildStack<*, Child>> = childStack(
        source = stackNavigation,
        initialConfiguration = ScreenConfig.Auth,
        handleBackButton = true,
        childFactory = ::createChild
    )


    private fun createChild(
        configuration: ScreenConfig,
        componentContext: ComponentContext,
    ): Child = when (configuration) {
        ScreenConfig.Auth -> {
            Child.Auth(authComponent(componentContext, ::onAuthOutput))
        }

        ScreenConfig.Home -> {
            Child.Home("")
        }

        ScreenConfig.Signup -> {
            Child.Signup(signupComponent(componentContext, ::onSignupOutput))
        }

        ScreenConfig.SignIn -> {
            Child.SignIn(signInComponent(componentContext, ::onSignInOutput))
        }
    }

    private fun onAuthOutput(output: AuthComponent.Output) {
        when (output) {
            AuthComponent.Output.Signup -> stackNavigation.push(ScreenConfig.Signup)
            AuthComponent.Output.SignIn -> stackNavigation.push(ScreenConfig.SignIn)
        }
    }

    private fun onSignupOutput(output: SignupComponent.Output) {
        when (output) {
            SignupComponent.Output.Back -> stackNavigation.pop()
        }
    }

    private fun onSignInOutput(output: SignInComponent.Output) {
        when (output) {
            SignInComponent.Output.Back -> stackNavigation.pop()
        }
    }

    private sealed class ScreenConfig : Parcelable {
        @Parcelize
        object Auth : ScreenConfig()

        @Parcelize
        object Home : ScreenConfig()

        @Parcelize
        object Signup : ScreenConfig()

        @Parcelize
        object SignIn : ScreenConfig()
    }

    @Parcelize
    private data class DialogConfig(
        val message: String,
    ) : Parcelable
}
