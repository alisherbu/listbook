package kaa.alisherbu.listbook.common.signup.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kaa.alisherbu.listbook.common.signup.store.Intent
import kaa.alisherbu.listbook.common.signup.store.SignupState
import kaa.alisherbu.listbook.common.signup.store.SignupStoreProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

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
}
