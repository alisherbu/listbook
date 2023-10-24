package kaa.alisherbu.listbook.feature.sign_in.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kaa.alisherbu.listbook.core.shared.coroutine.AppDispatchers
import kaa.alisherbu.listbook.feature.sign_in.store.Intent
import kaa.alisherbu.listbook.feature.sign_in.store.Label
import kaa.alisherbu.listbook.feature.sign_in.store.SignInState
import kaa.alisherbu.listbook.feature.sign_in.store.SignInStore
import kaa.alisherbu.listbook.feature.sign_in.component.SignInComponent.Output
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Provider

class SignInComponentImpl @AssistedInject internal constructor(
    @Assisted componentContext: ComponentContext,
    @Assisted private val output: (Output) -> Unit,
    private val storeProvider: Provider<SignInStore>,
    dispatchers: AppDispatchers
) : SignInComponent, ComponentContext by componentContext {
    private val store = instanceKeeper.getStore(storeProvider::get)
    private val mainScope = CoroutineScope(dispatchers.main)

    init {
        store.labels
            .onEach(::handleLabel)
            .launchIn(mainScope)
    }

    private fun handleLabel(label: Label) {
        when (label) {
            is Label.ErrorOccurred -> {
                output(Output.Error(label.message))
            }

            Label.SuccessfullySigned -> {
                output(Output.Main)
            }
        }
        lifecycle.doOnDestroy(mainScope::cancel)
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


    @AssistedFactory
    interface Factory : SignInComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            output: (Output) -> Unit
        ): SignInComponentImpl
    }
}
