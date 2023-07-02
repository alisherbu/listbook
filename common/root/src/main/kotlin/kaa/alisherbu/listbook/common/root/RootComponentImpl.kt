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
import kaa.alisherbu.listbook.common.auth.integration.AuthComponent
import kaa.alisherbu.listbook.common.auth.integration.AuthComponentImpl
import kaa.alisherbu.listbook.common.root.RootComponent.Child
import kaa.alisherbu.listbook.common.signup.SignupComponent
import kaa.alisherbu.listbook.common.signup.SignupComponentImpl
import kotlinx.parcelize.Parcelize

class RootComponentImpl(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val authComponent: (ComponentContext, (AuthComponent.Output) -> Unit) -> AuthComponent,
    private val signupComponent: (ComponentContext, (SignupComponent.Output) -> Unit) -> SignupComponent,
) : RootComponent, ComponentContext by componentContext {

    constructor(
        componentContext: ComponentContext,
        storeFactory: StoreFactory,
    ) : this(
        componentContext,
        storeFactory,
        authComponent = { childContext, output ->
            AuthComponentImpl(childContext, storeFactory, output)
        },
        signupComponent = { childContext, output ->
            SignupComponentImpl(childContext, storeFactory, output)
        }
    )

    private val navigation = StackNavigation<Configuration>()

    override val childStack: Value<ChildStack<*, Child>> = childStack(
        source = navigation,
        initialConfiguration = Configuration.Auth,
        handleBackButton = true,
        childFactory = ::createChild
    )

    private fun createChild(
        configuration: Configuration,
        componentContext: ComponentContext,
    ): Child = when (configuration) {
        is Configuration.Auth -> {
            Child.Auth(authComponent(componentContext, ::onAuthOutput))
        }

        is Configuration.Home -> Child.Home("")
        Configuration.Signup -> {
            Child.Signup(signupComponent(componentContext, ::onSignupOutput))
        }
    }

    private fun onAuthOutput(output: AuthComponent.Output) {
        when (output) {
            AuthComponent.Output.Signup -> navigation.push(Configuration.Signup)
        }
    }

    private fun onSignupOutput(output: SignupComponent.Output) {
        when (output) {
            SignupComponent.Output.Back -> navigation.pop()
        }
    }

    private sealed class Configuration : Parcelable {
        @Parcelize
        object Auth : Configuration()

        @Parcelize
        data class Home(val text: String) : Configuration()

        @Parcelize
        object Signup : Configuration()
    }
}
