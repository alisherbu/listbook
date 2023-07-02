package kaa.alisherbu.listbook.common.signup.store

import com.arkivanov.mvikotlin.core.store.Store

internal sealed interface Intent {
    class NameTextChanged(val text: String) : Intent
    class SurnameTextChanged(val text: String) : Intent
    class EmailTextChanged(val text: String) : Intent
    class PasswordTextChanged(val text: String) : Intent
}

internal sealed interface Label

internal sealed interface Message {
    class NameTextChanged(val text: String) : Message
    class SurnameTextChanged(val text: String) : Message
    class EmailTextChanged(val text: String) : Message
    class PasswordTextChanged(val text: String) : Message
}

internal interface AuthStore : Store<Intent, SignupState, Label>
