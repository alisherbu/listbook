package kaa.alisherbu.listbook.common.sign_in.component

import com.arkivanov.decompose.ComponentContext
import kaa.alisherbu.listbook.common.sign_in.store.SignInState
import kotlinx.coroutines.flow.StateFlow

interface SignInComponent {

    val state: StateFlow<SignInState>

    fun onBackClicked()

    fun onEmailTextChanged(text: String)

    fun onPasswordTextChanged(text: String)

    fun onLogInClicked()
    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            output: (SignInComponentImpl.Output) -> Unit
        ): SignInComponent
    }
}