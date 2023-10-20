package kaa.alisherbu.listbook.feature.sign_in.store

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kaa.alisherbu.listbook.feature.sign_in.domain.model.SignInResult
import kaa.alisherbu.listbook.feature.sign_in.domain.usecase.SignInUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class SignInExecutor @Inject constructor(
    private val signIn: SignInUseCase
) : CoroutineExecutor<Intent, Unit, SignInState, Message, Label>() {
    override fun executeIntent(intent: Intent, getState: () -> SignInState) {
        val state = getState()
        when (intent) {
            is Intent.EmailTextChanged -> dispatch(Message.EmailTextChanged(intent.text))
            is Intent.PasswordTextChanged -> dispatch(Message.PasswordTextChanged(intent.text))
            Intent.LogInClicked -> scope.launch {
                when (val result = signIn(state.email, state.password)) {
                    is SignInResult.Error -> publish(Label.ErrorOccurred(result.message))
                    is SignInResult.Success -> publish(Label.SuccessfullySigned)
                }
            }
        }
    }
}