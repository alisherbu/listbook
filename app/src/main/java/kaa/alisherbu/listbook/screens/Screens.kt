package kaa.alisherbu.listbook.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kaa.alisherbu.listbook.navigator.ChildStack

@Composable
fun MainContent() {
    val navigation = remember { StackNavigation<Screen>() }
    ChildStack(
        source = navigation,
        initialStack = { listOf(Screen.Auth) },
        animation = stackAnimation(fade() + scale()),
        handleBackButton = true
    ) { screen ->
        when (screen) {
            is Screen.Auth -> AuthScreen(navigation)
            is Screen.Home -> HomeScreen(screen.text)
            Screen.Signup -> SignupScreen(navigation)
        }
    }
}

sealed class Screen : Parcelable {

    @Parcelize
    object Auth : Screen()

    @Parcelize
    data class Home(val text: String) : Screen()

    @Parcelize
    object Signup : Screen()
}
