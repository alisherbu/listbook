package kaa.alisherbu.listbook.common.root.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import kaa.alisherbu.listbook.common.auth.ui.AuthScreen
import kaa.alisherbu.listbook.common.dialog.ui.MessageDialogScreen
import kaa.alisherbu.listbook.common.home.HomeScreen
import kaa.alisherbu.listbook.common.root.component.RootComponentImpl
import kaa.alisherbu.listbook.common.sign_in.ui.SignInScreen
import kaa.alisherbu.listbook.common.signup.ui.SignupScreen

@Composable
fun RootContent(component: RootComponentImpl) {
    Children(
        stack = component.screenStack,
        animation = stackAnimation(fade() + scale()),
    ) {
        when (val child = it.instance) {
            is RootComponentImpl.ChildScreen.Auth -> AuthScreen(child.component)
            is RootComponentImpl.ChildScreen.Home -> HomeScreen("screen.text")
            is RootComponentImpl.ChildScreen.Signup -> SignupScreen(child.component)
            is RootComponentImpl.ChildScreen.SignIn -> SignInScreen(child.component)
        }
    }

    val dialogSlot by component.dialogSlot.subscribeAsState()
    dialogSlot.child?.instance?.also { childDialog ->
        when (childDialog) {
            is RootComponentImpl.ChildDialog.Message -> {
                MessageDialogScreen(childDialog.component)
            }
        }
    }
}
