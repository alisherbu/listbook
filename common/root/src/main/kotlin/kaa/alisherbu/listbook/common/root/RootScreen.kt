package kaa.alisherbu.listbook.common.root

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation
import kaa.alisherbu.listbook.common.auth.ui.AuthScreen
import kaa.alisherbu.listbook.common.signup.SignupScreen
import kaa.alisherbu.listbook.common.home.HomeScreen

@Composable
fun RootContent(component: ListbookRoot) {
    Children(
        stack = component.childStack,
        animation = stackAnimation(fade() + scale()),
    ) {
        when (val child = it.instance) {
            is ListbookRoot.Child.Auth -> AuthScreen(child.component)
            is ListbookRoot.Child.Home -> HomeScreen("screen.text")
            ListbookRoot.Child.Signup -> SignupScreen()
        }
    }
}
