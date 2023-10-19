package kaa.alisherbu.listbook.common.signup.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kaa.alisherbu.listbook.common.signup.store.Intent
import kaa.alisherbu.listbook.common.signup.store.Label
import kaa.alisherbu.listbook.common.signup.store.SignupState
import kaa.alisherbu.listbook.common.signup.store.SignupStore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.parcelize.Parcelize
import javax.inject.Provider

class SignupComponentImpl @AssistedInject internal constructor(
    @Assisted componentContext: ComponentContext,
    @Assisted private val output: (Output) -> Unit,
    private val storeProvider: Provider<SignupStore>
) : SignupComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        storeProvider.get()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val state: StateFlow<SignupState> = store.stateFlow
    private val dialogNavigation = SlotNavigation<DialogConfig>()
    override val dialogSlot: Value<ChildSlot<*, ChildDialog>> = childSlot(
        source = dialogNavigation,
        handleBackButton = true,
        childFactory = ::createChildDialog
    )

    init {
        store.labels
            .onEach(::handleLabel)
            .launchIn(MainScope())
    }

    private fun handleLabel(label: Label) {
        when (label) {
            Label.AccountSuccessfullyCreated -> {
                dialogNavigation.activate(DialogConfig.SuccessDialogConfig("Account successfully created"))
            }

            is Label.ErrorOccurred -> {
                output(Output.Error(label.message))
            }
        }
    }

    override fun onNameTextChanged(text: String) {
        store.accept(Intent.NameTextChanged(text))
    }

    override fun onSurnameTextChanged(text: String) {
        store.accept(Intent.SurnameTextChanged(text))
    }

    override fun onEmailTextChanged(text: String) {
        store.accept(Intent.EmailTextChanged(text))
    }

    override fun onPasswordTextChanged(text: String) {
        store.accept(Intent.PasswordTextChanged(text))
    }

    override fun onBackClicked() {
        output(Output.Back)
    }

    override fun onCreateAccountClicked() {
        store.accept(Intent.CreateAccountClicked)
    }

    private fun createChildDialog(
        config: DialogConfig,
        componentContext: ComponentContext
    ): ChildDialog = when (config) {
        is DialogConfig.SuccessDialogConfig -> {
            ChildDialog.Success(
                SuccessDialogComponent(
                    componentContext,
                    config.message,
                    onDismissed = {
                        dialogNavigation.dismiss()
                        output(Output.Back)
                    }
                )
            )
        }
    }

    sealed interface ChildDialog {
        class Success(val component: SuccessDialogComponent) : ChildDialog
    }

    private sealed interface DialogConfig : Parcelable {
        @Parcelize
        class SuccessDialogConfig(
            val message: String,
        ) : DialogConfig
    }

    sealed class Output {
        object Back : Output()
        class Error(val message: String) : Output()
    }

    @AssistedFactory
    interface Factory : SignupComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            output: (Output) -> Unit
        ): SignupComponentImpl
    }
}
