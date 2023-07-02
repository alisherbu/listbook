package kaa.alisherbu.listbook.common.root

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation
import kaa.alisherbu.listbook.common.auth.ui.AuthScreen
import kaa.alisherbu.listbook.common.home.HomeScreen
import kaa.alisherbu.listbook.common.signup.SignupScreen

@Composable
fun RootContent(component: RootComponent) {
    Children(
        stack = component.childStack,
        animation = stackAnimation(fade() + scale()),
    ) {
        when (val child = it.instance) {
            is RootComponent.Child.Auth -> AuthScreen(child.component)
            is RootComponent.Child.Home -> HomeScreen("screen.text")
            is RootComponent.Child.Signup -> SignupScreen(child.component)
        }
    }
}