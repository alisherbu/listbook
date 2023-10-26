package kaa.alisherbu.listbook.feature.sign_in.store

import com.arkivanov.mvikotlin.core.store.Reducer
import javax.inject.Inject

internal class SignInReducer @Inject constructor() : Reducer<SignInState, Message> {
    override fun SignInState.reduce(msg: Message): SignInState {
        return when (msg) {
            is Message.EmailTextChanged -> copy(email = msg.text)
            is Message.PasswordTextChanged -> copy(password = msg.text)
        }
    }
}
