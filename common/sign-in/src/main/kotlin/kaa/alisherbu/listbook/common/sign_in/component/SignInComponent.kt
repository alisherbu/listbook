package kaa.alisherbu.listbook.common.sign_in.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kaa.alisherbu.listbook.common.sign_in.store.Intent
import kaa.alisherbu.listbook.common.sign_in.store.Label
import kaa.alisherbu.listbook.common.sign_in.store.SignInState
import kaa.alisherbu.listbook.common.sign_in.store.SignInStoreProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

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
                output(Output.Error(label.message))
            }

            Label.SuccessfullySigned -> {
                output(Output.Home)
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<SignInState> = store.stateFlow

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

    sealed interface Output {
        object Back : Output
        object Home : Output
        class Error(val message: String) : Output
    }
}
