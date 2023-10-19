package kaa.alisherbu.listbook.common.sign_in.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kaa.alisherbu.listbook.common.sign_in.store.Intent
import kaa.alisherbu.listbook.common.sign_in.store.Label
import kaa.alisherbu.listbook.common.sign_in.store.SignInState
import kaa.alisherbu.listbook.common.sign_in.store.SignInStore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Provider

class SignInComponentImpl @AssistedInject internal constructor(
    @Assisted componentContext: ComponentContext,
    @Assisted private val output: (Output) -> Unit,
    private val storeProvider: Provider<SignInStore>
) : SignInComponent, ComponentContext by componentContext {
    private val store = instanceKeeper.getStore { storeProvider.get() }

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
    override val state: StateFlow<SignInState> = store.stateFlow

    override fun onBackClicked() {
        output(Output.Back)
    }

    override fun onEmailTextChanged(text: String) {
        store.accept(Intent.EmailTextChanged(text))
    }

    override fun onPasswordTextChanged(text: String) {
        store.accept(Intent.PasswordTextChanged(text))
    }

    override fun onLogInClicked() {
        store.accept(Intent.LogInClicked)
    }

    sealed interface Output {
        object Back : Output
        object Home : Output
        class Error(val message: String) : Output
    }

    @AssistedFactory
    interface Factory : SignInComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            output: (Output) -> Unit
        ): SignInComponentImpl
    }
}
