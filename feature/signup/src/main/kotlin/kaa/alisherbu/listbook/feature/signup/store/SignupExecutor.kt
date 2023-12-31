package kaa.alisherbu.listbook.feature.signup.store

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kaa.alisherbu.listbook.feature.signup.domain.model.SignupResult
import kaa.alisherbu.listbook.feature.signup.domain.usecase.SignUpUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class SignupExecutor @Inject constructor(
    private val signUp: SignUpUseCase,
) : CoroutineExecutor<Intent, Unit, SignupState, Message, Label>() {
    override fun executeIntent(intent: Intent, getState: () -> SignupState) {
        val state = getState()
        when (intent) {
            is Intent.EmailTextChanged -> {
                val isCreateAccountButtonEnabled = isNotBlank(
                    name = state.name,
                    surname = state.surname,
                    email = intent.text,
                    password = state.password,
                )
                dispatch(Message.EmailTextChanged(intent.text, isCreateAccountButtonEnabled))
            }

            is Intent.NameTextChanged -> {
                val isCreateAccountButtonEnabled = isNotBlank(
                    name = intent.text,
                    surname = state.surname,
                    email = state.email,
                    password = state.password,
                )
                dispatch(Message.NameTextChanged(intent.text, isCreateAccountButtonEnabled))
            }

            is Intent.PasswordTextChanged -> {
                val isCreateAccountButtonEnabled = isNotBlank(
                    name = state.name,
                    surname = state.surname,
                    email = state.email,
                    password = intent.text,
                )
                dispatch(Message.PasswordTextChanged(intent.text, isCreateAccountButtonEnabled))
            }

            is Intent.SurnameTextChanged -> {
                val isCreateAccountButtonEnabled = isNotBlank(
                    name = state.name,
                    surname = intent.text,
                    email = state.email,
                    password = state.password,
                )
                dispatch(Message.SurnameTextChanged(intent.text, isCreateAccountButtonEnabled))
            }

            Intent.CreateAccountClicked -> scope.launch {
                when (val result = signUp(state.email, state.password)) {
                    is SignupResult.Error -> publish(Label.ErrorOccurred(result.message))
                    is SignupResult.Success -> publish(Label.AccountSuccessfullyCreated)
                }
            }
        }
    }

    private fun isNotBlank(
        name: String,
        surname: String,
        email: String,
        password: String,
    ): Boolean {
        return name.isNotBlank() && surname.isNotBlank() &&
            email.isNotBlank() && password.isNotBlank()
    }
}
