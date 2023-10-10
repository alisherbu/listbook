package kaa.alisherbu.listbook.common.sign_in.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kaa.alisherbu.listbook.common.dialog.component.MessageDialogComponent
import kaa.alisherbu.listbook.common.sign_in.store.Intent
import kaa.alisherbu.listbook.common.sign_in.store.Label
import kaa.alisherbu.listbook.common.sign_in.store.SignInState
import kaa.alisherbu.listbook.common.sign_in.store.SignInStoreProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.parcelize.Parcelize

class SignInComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val output: (Output) -> Unit
) : ComponentContext by componentContext {
    private val store = instanceKeeper.getStore {
        SignInStoreProvider(storeFactory).provide()
    }

    init {
        store.labels
            .onEach(::handleLabel)
            .launchIn(MainScope())
    }

    private fun handleLabel(label: Label) {
        when (label) {
            is Label.ErrorOccurred -> {
                dialogNavigation.activate(DialogConfig.MessageDialogConfig(label.message))
            }

            Label.SuccessfullySigned -> {
                output(Output.Home)
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<SignInState> = store.stateFlow
    private val dialogNavigation = SlotNavigation<DialogConfig>()
    internal val dialogSlot: Value<ChildSlot<*, ChildDialog>> = childSlot(
        source = dialogNavigation,
        handleBackButton = true,
        childFactory = ::createChildDialog
    )

    fun onBackClicked() {
        output(Output.Back)
    }

    fun onEmailTextChanged(text: String) {
        store.accept(Intent.EmailTextChanged(text))
    }

    fun onPasswordTextChanged(text: String) {
        store.accept(Intent.PasswordTextChanged(text))
    }

    fun onLogInClicked() {
        store.accept(Intent.LogInClicked)
    }

    private fun createChildDialog(
        config: DialogConfig,
        componentContext: ComponentContext
    ): ChildDialog = when (config) {
        is DialogConfig.MessageDialogConfig -> {
            ChildDialog.Message(
                MessageDialogComponent(
                    componentContext,
                    config.message,
                    onDismissed = dialogNavigation::dismiss
                )
            )
        }
    }

    sealed interface Output {
        object Back : Output
        object Home : Output
    }

    internal sealed interface ChildDialog {
        class Message(val component: MessageDialogComponent) : ChildDialog
    }

    private sealed interface DialogConfig : Parcelable {
        @Parcelize
        class MessageDialogConfig(
            val message: String,
        ) : DialogConfig
    }
}
