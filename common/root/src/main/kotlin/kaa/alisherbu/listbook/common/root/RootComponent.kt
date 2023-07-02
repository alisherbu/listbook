package kaa.alisherbu.listbook.common.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import kaa.alisherbu.listbook.common.auth.integration.AuthComponent
import kaa.alisherbu.listbook.common.signup.SignupComponent

interface RootComponent {
    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        class Auth(val component: AuthComponent) : Child()

        data class Home(val text: String) : Child()
        class Signup(val component: SignupComponent) : Child()
    }
}
