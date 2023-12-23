package kaa.alisherbu.listbook.feature.signup.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kaa.alisherbu.listbook.core.shared.coroutine.AppDispatchers
import kaa.alisherbu.listbook.feature.signup.component.SignupComponent.Output
import kaa.alisherbu.listbook.feature.signup.store.Intent
import kaa.alisherbu.listbook.feature.signup.store.Label
import kaa.alisherbu.listbook.feature.signup.store.SignupState
import kaa.alisherbu.listbook.feature.signup.store.SignupStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Provider

class SignupComponentImpl @AssistedInject internal constructor(
    @Assisted componentContext: ComponentContext,
    @Assisted private val output: (Output) -> Unit,
    private val storeProvider: Provider<SignupStore>,
    dispatchers: AppDispatchers,
) : SignupComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore(storeProvider::get)
    private val mainScope = CoroutineScope(dispatchers.main)

    @OptIn(ExperimentalCoroutinesApi::class)
    override val state: StateFlow<SignupState> = store.stateFlow

    init {
        store.labels
            .onEach(::handleLabel)
            .launchIn(mainScope)
        lifecycle.doOnDestroy(mainScope::cancel)
    }

    private fun handleLabel(label: Label) {
        when (label) {
            Label.AccountSuccessfullyCreated -> {
                output(Output.Main)
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

    @AssistedFactory
    interface Factory : SignupComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            output: (Output) -> Unit,
        ): SignupComponentImpl
    }
}
