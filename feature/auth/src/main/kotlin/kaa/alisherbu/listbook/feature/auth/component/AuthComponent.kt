package kaa.alisherbu.listbook.feature.auth.component

import com.arkivanov.decompose.ComponentContext

interface AuthComponent {

    fun onSignupClicked()

    fun onSignInClicked()

    sealed class Output {
        object Signup : Output()
        object SignIn : Output()
    }

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            output: (Output) -> Unit,
        ): AuthComponent
    }
}
