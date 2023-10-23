package kaa.alisherbu.listbook.feature.root.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import kaa.alisherbu.feature.player.ui.PlayerScreen
import kaa.alisherbu.listbook.feature.auth.ui.AuthScreen
import kaa.alisherbu.listbook.feature.dialog.ui.MessageDialogScreen
import kaa.alisherbu.listbook.feature.main.ui.MainScreen
import kaa.alisherbu.listbook.feature.root.component.RootComponent
import kaa.alisherbu.listbook.feature.root.component.RootComponent.ChildScreen
import kaa.alisherbu.listbook.feature.root.component.RootComponent.ChildDialog
import kaa.alisherbu.listbook.feature.sign_in.ui.SignInScreen
import kaa.alisherbu.listbook.feature.signup.ui.SignupScreen

@Composable
fun RootScreen(component: RootComponent) {
    Children(
        stack = component.screenStack,
        animation = stackAnimation(fade() + scale()),
    ) {
        when (val child = it.instance) {
            is ChildScreen.Auth -> AuthScreen(child.component)
            is ChildScreen.Main -> MainScreen(child.component)
            is ChildScreen.Signup -> SignupScreen(child.component)
            is ChildScreen.SignIn -> SignInScreen(child.component)
            is ChildScreen.Player -> PlayerScreen(child.component)
            ChildScreen.Undefined -> ProgressIndicator()
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

@Composable
internal fun ProgressIndicator() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(80.dp)
                .padding(16.dp)
        )
    }
}
