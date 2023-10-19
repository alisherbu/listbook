package kaa.alisherbu.listbook.common.sign_in.store

import com.arkivanov.mvikotlin.core.store.Reducer

internal class SignInReducer : Reducer<SignInState, Message> {
    override fun SignInState.reduce(msg: Message): SignInState {
        return when (msg) {
            is Message.EmailTextChanged -> copy(email = msg.text)
            is Message.PasswordTextChanged -> copy(password = msg.text)
        }
    }
}