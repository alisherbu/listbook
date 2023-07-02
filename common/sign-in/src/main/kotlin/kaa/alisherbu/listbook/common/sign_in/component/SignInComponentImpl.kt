package kaa.alisherbu.listbook.common.sign_in.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kaa.alisherbu.listbook.common.sign_in.component.SignInComponent.Output
import kaa.alisherbu.listbook.common.sign_in.store.Intent
import kaa.alisherbu.listbook.common.sign_in.store.SignInState
import kaa.alisherbu.listbook.common.sign_in.store.SignInStoreProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

class SignInComponentImpl(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val output: (Output) -> Unit
) : SignInComponent, ComponentContext by componentContext {
    private val store = instanceKeeper.getStore {
        SignInStoreProvider(storeFactory).provide()
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
}
