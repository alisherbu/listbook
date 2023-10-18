package kaa.alisherbu.listbook.common.signup.store

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kaa.alisherbu.listbook.auth_manager.AuthManager
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class SignupExecutor :
    CoroutineExecutor<Intent, Unit, SignupState, Message, Label>(), KoinComponent {
    private val authManager: AuthManager by inject()
    override fun executeIntent(intent: Intent, getState: () -> SignupState) {
        val state = getState()
        when (intent) {
            is Intent.EmailTextChanged -> {
                val isCreateAccountButtonEnabled = isNotBlank(
                    state.name,
                    state.surname,
                    intent.text,
                    state.password
                )
                dispatch(Message.EmailTextChanged(intent.text, isCreateAccountButtonEnabled))
            }

            is Intent.NameTextChanged -> {
                val isCreateAccountButtonEnabled = isNotBlank(
                    intent.text,
                    state.surname,
                    state.email,
                    state.password
                )
                dispatch(Message.NameTextChanged(intent.text, isCreateAccountButtonEnabled))
            }

            is Intent.PasswordTextChanged -> {
                val isCreateAccountButtonEnabled = isNotBlank(
                    state.name,
                    state.surname,
                    state.email,
                    intent.text
                )
                dispatch(Message.PasswordTextChanged(intent.text, isCreateAccountButtonEnabled))
            }

            is Intent.SurnameTextChanged -> {
                val isCreateAccountButtonEnabled = isNotBlank(
                    state.name,
                    intent.text,
                    state.email,
                    state.password
                )
                dispatch(Message.SurnameTextChanged(intent.text, isCreateAccountButtonEnabled))
            }

            Intent.CreateAccountClicked -> scope.launch {
                try {
                    val user = authManager.createUser(state.email, state.password)
                    if (user != null) publish(Label.AccountSuccessfullyCreated)
                    else publish(Label.ErrorOccurred("Something wrong"))
                } catch (e: Exception) {
                    publish(Label.ErrorOccurred(e.message.toString()))
                }

            }
        }
    }

    private fun isNotBlank(
        name: String,
        surname: String,
        email: String,
        password: String
    ): Boolean {
        return name.isNotBlank() && surname.isNotBlank() &&
                email.isNotBlank() && password.isNotBlank()
    }
}