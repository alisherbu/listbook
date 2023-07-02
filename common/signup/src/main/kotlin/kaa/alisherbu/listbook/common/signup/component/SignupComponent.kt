package kaa.alisherbu.listbook.common.signup.component

import kaa.alisherbu.listbook.common.signup.store.SignupState
import kotlinx.coroutines.flow.StateFlow

interface SignupComponent {
    val state: StateFlow<SignupState>

    fun onNameTextChanged(text: String)
    fun onSurnameTextChanged(text: String)
    fun onEmailTextChanged(text: String)
    fun onPasswordTextChanged(text: String)
    fun onBackClicked()

    fun onCreateAccountClicked()

    sealed class Output {
        object Back : Output()
    }
}
