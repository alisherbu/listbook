package kaa.alisherbu.listbook.feature.sign_in.component

import com.arkivanov.decompose.ComponentContext
import kaa.alisherbu.listbook.feature.sign_in.store.SignInState
import kotlinx.coroutines.flow.StateFlow

interface SignInComponent {

    val state: StateFlow<SignInState>

    fun onBackClicked()

    fun onEmailTextChanged(text: String)

    fun onPasswordTextChanged(text: String)

    fun onLogInClicked()

    sealed interface Output {
        data object Back : Output
        data object Main : Output
        class Error(val message: String) : Output
    }

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            output: (Output) -> Unit,
        ): SignInComponent
    }
}
