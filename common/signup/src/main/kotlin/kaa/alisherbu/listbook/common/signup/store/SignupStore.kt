package kaa.alisherbu.listbook.common.signup.store

import com.arkivanov.mvikotlin.core.store.Store

internal sealed interface Intent {
    class NameTextChanged(val text: String) : Intent
    class SurnameTextChanged(val text: String) : Intent
    class EmailTextChanged(val text: String) : Intent
    class PasswordTextChanged(val text: String) : Intent

    object CreateAccountClicked : Intent
}

internal sealed interface Label

internal sealed interface Message {
    class NameTextChanged(val text: String, val isCreateAccountButtonEnabled: Boolean) : Message
    class SurnameTextChanged(val text: String, val isCreateAccountButtonEnabled: Boolean) : Message
    class EmailTextChanged(val text: String, val isCreateAccountButtonEnabled: Boolean) : Message
    class PasswordTextChanged(val text: String, val isCreateAccountButtonEnabled: Boolean) : Message
}

internal interface SignupStore : Store<Intent, SignupState, Label>
