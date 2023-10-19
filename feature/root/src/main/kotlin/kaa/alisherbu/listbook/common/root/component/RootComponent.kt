package kaa.alisherbu.listbook.common.root.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import kaa.alisherbu.listbook.common.auth.component.AuthComponent
import kaa.alisherbu.listbook.common.dialog.component.MessageDialogComponent
import kaa.alisherbu.listbook.common.sign_in.component.SignInComponent
import kaa.alisherbu.listbook.common.signup.component.SignupComponent

interface RootComponent {

    val screenStack: Value<ChildStack<*, ChildScreen>>
    val dialogSlot: Value<ChildSlot<*, ChildDialog>>

    sealed interface ChildScreen {
        class Auth(val component: AuthComponent) : ChildScreen
        class Home(val text: String) : ChildScreen
        class Signup(val component: SignupComponent) : ChildScreen
        class SignIn(val component: SignInComponent) : ChildScreen
    }

    sealed interface ChildDialog {
        class Message(val component: MessageDialogComponent) : ChildDialog
    }

    fun interface Factory {
        operator fun invoke(componentContext: ComponentContext): RootComponent
    }
}