package kaa.alisherbu.listbook.common.signup.component

import kaa.alisherbu.listbook.common.dialog.component.DialogComponent
import kaa.alisherbu.listbook.common.dialog.component.DialogComponentImpl
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
import kaa.alisherbu.listbook.common.signup.store.Intent
import kaa.alisherbu.listbook.common.signup.store.Label
import kaa.alisherbu.listbook.common.signup.store.SignupState
import kaa.alisherbu.listbook.common.signup.store.SignupStoreProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.parcelize.Parcelize

class SignupComponentImpl(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val output: (SignupComponent.Output) -> Unit,
) : SignupComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        SignupStoreProvider(storeFactory).provide()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val state: StateFlow<SignupState> = store.stateFlow
    private val dialogNavigation = SlotNavigation<DialogConfig>()
    override val dialogSlot: Value<ChildSlot<*, DialogComponent>> = childSlot(
        source = dialogNavigation,
        handleBackButton = true,
    ) { config, childComponentContext ->
        DialogComponentImpl(
            childComponentContext,
            config.message,
            onDismissed = dialogNavigation::dismiss
        )
    }

    init {
        store.labels
            .onEach(::handleLabel)
            .launchIn(MainScope())
    }

    private fun handleLabel(label: Label) {
        when (label) {
            Label.AccountSuccessfullyCreated -> {
                dialogNavigation.activate(DialogConfig("Account successfully created"))
            }

            is Label.ErrorOccurred -> {
                dialogNavigation.activate(DialogConfig(label.message))
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
        output(SignupComponent.Output.Back)
    }

    override fun onCreateAccountClicked() {
        store.accept(Intent.CreateAccountClicked)
    }

    @Parcelize
    private data class DialogConfig(
        val message: String,
    ) : Parcelable
}
