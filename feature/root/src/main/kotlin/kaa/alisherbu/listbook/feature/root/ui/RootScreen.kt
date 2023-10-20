package kaa.alisherbu.listbook.feature.root.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import kaa.alisherbu.listbook.feature.auth.ui.AuthScreen
import kaa.alisherbu.listbook.feature.dialog.ui.MessageDialogScreen
import kaa.alisherbu.listbook.feature.main.ui.MainScreen
import kaa.alisherbu.listbook.feature.root.component.RootComponent
import kaa.alisherbu.listbook.feature.root.component.RootComponent.ChildScreen
import kaa.alisherbu.listbook.feature.root.component.RootComponent.ChildDialog
import kaa.alisherbu.listbook.feature.sign_in.ui.SignInScreen
import kaa.alisherbu.listbook.feature.signup.ui.SignupScreen

@Composable
fun RootContent(component: RootComponent) {
    Children(
        stack = component.screenStack,
        animation = stackAnimation(fade() + scale()),
    ) {
        when (val child = it.instance) {
            is ChildScreen.Auth -> AuthScreen(child.component)
            is ChildScreen.Main -> MainScreen(child.component)
            is ChildScreen.Signup -> SignupScreen(child.component)
            is ChildScreen.SignIn -> SignInScreen(child.component)
        }
    }

    val dialogSlot by component.dialogSlot.subscribeAsState()
    dialogSlot.child?.instance?.also { childDialog ->
        when (childDialog) {
            is ChildDialog.Message -> {
                MessageDialogScreen(childDialog.component)
            }
        }
    }
}
