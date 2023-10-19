package kaa.alisherbu.listbook.common.sign_in.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kaa.alisherbu.listbook.auth_manager.AuthManager
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class SignInStoreProvider(
    private val storeFactory: StoreFactory,
) {

    fun provide(): SignInStore =
        object :
            SignInStore,
            Store<Intent, SignInState, Label> by storeFactory.create(
                name = "SignInStore",
                executorFactory = ::SignInExecutor,
                reducer = SignInReducer,
                initialState = SignInState()
            ) {}

    private inner class SignInExecutor :
        CoroutineExecutor<Intent, Unit, SignInState, Message, Label>(), KoinComponent {
        private val authManager: AuthManager by inject()
        override fun executeIntent(intent: Intent, getState: () -> SignInState) {
            val state = getState()
            when (intent) {
                is Intent.EmailTextChanged -> dispatch(Message.EmailTextChanged(intent.text))
                is Intent.PasswordTextChanged -> dispatch(Message.PasswordTextChanged(intent.text))
                Intent.LogInClicked -> scope.launch {
                    try {
                        val user = authManager.signInUser(state.email, state.password)
                        if (user != null) publish(Label.SuccessfullySigned)
                        else publish(Label.ErrorOccurred("Something wrong"))
                    } catch (e: Exception) {
                        publish(Label.ErrorOccurred(e.message.toString()))
                    }
                }
            }
        }
    }

    internal object SignInReducer : Reducer<SignInState, Message> {
        override fun SignInState.reduce(msg: Message): SignInState {
            return when (msg) {
                is Message.EmailTextChanged -> copy(email = msg.text)
                is Message.PasswordTextChanged -> copy(password = msg.text)
            }
        }
    }
}
