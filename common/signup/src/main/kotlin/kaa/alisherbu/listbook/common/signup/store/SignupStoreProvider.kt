package kaa.alisherbu.listbook.common.signup.store

import android.util.Log
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kaa.alisherbu.listbook.auth_manager.AuthManager
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class SignupStoreProvider(
    private val storeFactory: StoreFactory,
) {

    fun provide(): SignupStore =
        object :
            SignupStore,
            Store<Intent, SignupState, Label> by storeFactory.create(
                name = "SignupStore",
                executorFactory = ::SignupExecutor,
                reducer = SignReducer,
                initialState = SignupState()
            ) {}

    private inner class SignupExecutor :
        CoroutineExecutor<Intent, Unit, SignupState, Message, Label>(), KoinComponent {
        private val authManager: AuthManager by inject()
        override fun executeIntent(intent: Intent, getState: () -> SignupState) {
            val state = getState()
            when (intent) {
                is Intent.EmailTextChanged -> dispatch(Message.EmailTextChanged(intent.text))
                is Intent.NameTextChanged -> dispatch(Message.NameTextChanged(intent.text))
                is Intent.PasswordTextChanged -> dispatch(Message.PasswordTextChanged(intent.text))
                is Intent.SurnameTextChanged -> dispatch(Message.SurnameTextChanged(intent.text))
                Intent.CreateAccountClicked -> scope.launch {
                    authManager.createUserWithEmailAndPassword(state.email, state.password)
                        .onSuccess {
                            Log.d("SignupExecutor", it.toString())
                        }
                        .onFailure {
                            Log.d("SignupExecutor", it.message.toString())
                        }
                }
            }
        }
    }

    internal object SignReducer : Reducer<SignupState, Message> {
        override fun SignupState.reduce(msg: Message): SignupState {
            return when (msg) {
                is Message.EmailTextChanged -> copy(email = msg.text)
                is Message.NameTextChanged -> copy(name = msg.text)
                is Message.PasswordTextChanged -> copy(password = msg.text)
                is Message.SurnameTextChanged -> copy(surname = msg.text)
            }
        }
    }
}
