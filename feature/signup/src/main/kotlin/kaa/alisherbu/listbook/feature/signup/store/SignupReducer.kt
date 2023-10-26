package kaa.alisherbu.listbook.feature.signup.store

import com.arkivanov.mvikotlin.core.store.Reducer
import javax.inject.Inject

internal class SignupReducer @Inject constructor() : Reducer<SignupState, Message> {
    override fun SignupState.reduce(msg: Message): SignupState {
        return when (msg) {
            is Message.EmailTextChanged -> {
                copy(
                    email = msg.text,
                    isCreateAccountButtonEnabled = msg.isCreateAccountButtonEnabled,
                )
            }

            is Message.NameTextChanged -> {
                copy(
                    name = msg.text,
                    isCreateAccountButtonEnabled = msg.isCreateAccountButtonEnabled,
                )
            }

            is Message.PasswordTextChanged -> {
                copy(
                    password = msg.text,
                    isCreateAccountButtonEnabled = msg.isCreateAccountButtonEnabled,
                )
            }

            is Message.SurnameTextChanged -> {
                copy(
                    surname = msg.text,
                    isCreateAccountButtonEnabled = msg.isCreateAccountButtonEnabled,
                )
            }
        }
    }
}
