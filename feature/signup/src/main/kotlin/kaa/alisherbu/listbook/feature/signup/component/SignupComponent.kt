package kaa.alisherbu.listbook.feature.signup.component

import com.arkivanov.decompose.ComponentContext
import kaa.alisherbu.listbook.feature.signup.store.SignupState
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
        data object Back : Output()
        data object Main : Output()
        class Error(val message: String) : Output()
    }

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            output: (Output) -> Unit,
        ): SignupComponent
    }
}
