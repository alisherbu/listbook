package kaa.alisherbu.listbook.common.signup.component

import kaa.alisherbu.listbook.common.dialog.component.DialogComponent
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import kaa.alisherbu.listbook.common.signup.store.SignupState
import kotlinx.coroutines.flow.StateFlow

interface SignupComponent {
    val state: StateFlow<SignupState>
    val dialogSlot: Value<ChildSlot<*, DialogComponent>>
    fun onNameTextChanged(text: String)
    fun onSurnameTextChanged(text: String)
    fun onEmailTextChanged(text: String)
    fun onPasswordTextChanged(text: String)
    fun onBackClicked()

    fun onCreateAccountClicked()

    sealed class Output {
        object Back : Output()
    }
}
