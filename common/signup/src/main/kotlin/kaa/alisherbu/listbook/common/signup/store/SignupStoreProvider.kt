package kaa.alisherbu.listbook.common.signup.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor

internal class SignupStoreProvider(
    private val storeFactory: StoreFactory,
) {

    fun provide(): AuthStore =
        object :
            AuthStore,
            Store<Intent, SignupState, Label> by storeFactory.create(
                name = "SignupStore",
                executorFactory = ::SignupExecutor,
                reducer = SignReducer,
                initialState = SignupState()
            ) {}

    private inner class SignupExecutor :
        CoroutineExecutor<Intent, Unit, SignupState, Message, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> SignupState) {
            when (intent) {
                is Intent.EmailTextChanged -> dispatch(Message.EmailTextChanged(intent.text))
                is Intent.NameTextChanged -> dispatch(Message.NameTextChanged(intent.text))
                is Intent.PasswordTextChanged -> dispatch(Message.PasswordTextChanged(intent.text))
                is Intent.SurnameTextChanged -> dispatch(Message.SurnameTextChanged(intent.text))
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
