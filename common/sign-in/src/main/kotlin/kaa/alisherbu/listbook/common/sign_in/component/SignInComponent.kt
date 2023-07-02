package kaa.alisherbu.listbook.common.sign_in.component

import kaa.alisherbu.listbook.common.sign_in.store.SignInState
import kotlinx.coroutines.flow.StateFlow

interface SignInComponent {
    val state: StateFlow<SignInState>

    fun onBackClicked()

    fun onEmailTextChanged(text: String)

    fun onPasswordTextChanged(text: String)

    fun onLogInClicked()

    sealed interface Output {
        object Back : Output
    }
}
