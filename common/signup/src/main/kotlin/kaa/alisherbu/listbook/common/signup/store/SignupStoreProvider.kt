package kaa.alisherbu.listbook.common.signup.store

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
                is Intent.EmailTextChanged -> {
                    val isCreateAccountButtonEnabled = isNotBlank(
                        state.name,
                        state.surname,
                        intent.text,
                        state.password
                    )
                    dispatch(Message.EmailTextChanged(intent.text, isCreateAccountButtonEnabled))
                }

                is Intent.NameTextChanged -> {
                    val isCreateAccountButtonEnabled = isNotBlank(
                        intent.text,
                        state.surname,
                        state.email,
                        state.password
                    )
                    dispatch(Message.NameTextChanged(intent.text, isCreateAccountButtonEnabled))
                }

                is Intent.PasswordTextChanged -> {
                    val isCreateAccountButtonEnabled = isNotBlank(
                        state.name,
                        state.surname,
                        state.email,
                        intent.text
                    )
                    dispatch(Message.PasswordTextChanged(intent.text, isCreateAccountButtonEnabled))
                }

                is Intent.SurnameTextChanged -> {
                    val isCreateAccountButtonEnabled = isNotBlank(
                        state.name,
                        intent.text,
                        state.email,
                        state.password
                    )
                    dispatch(Message.SurnameTextChanged(intent.text, isCreateAccountButtonEnabled))
                }

                Intent.CreateAccountClicked -> scope.launch {
                    try {
                        val user = authManager.createUser(state.email, state.password)
                        if (user != null) publish(Label.AccountSuccessfullyCreated)
                        else publish(Label.ErrorOccurred("Something wrong"))
                    } catch (e: Exception) {
                        publish(Label.ErrorOccurred(e.message.toString()))
                    }

                }
            }
        }

        private fun isNotBlank(
            name: String,
            surname: String,
            email: String,
            password: String
        ): Boolean {
            return name.isNotBlank() && surname.isNotBlank() &&
                    email.isNotBlank() && password.isNotBlank()
        }
    }

    internal object SignReducer : Reducer<SignupState, Message> {
        override fun SignupState.reduce(msg: Message): SignupState {
            return when (msg) {
                is Message.EmailTextChanged -> {
                    copy(
                        email = msg.text,
                        isCreateAccountButtonEnabled = msg.isCreateAccountButtonEnabled
                    )
                }

                is Message.NameTextChanged -> {
                    copy(
                        name = msg.text,
                        isCreateAccountButtonEnabled = msg.isCreateAccountButtonEnabled
                    )
                }

                is Message.PasswordTextChanged -> {
                    copy(
                        password = msg.text,
                        isCreateAccountButtonEnabled = msg.isCreateAccountButtonEnabled
                    )
                }

                is Message.SurnameTextChanged -> {
                    copy(
                        surname = msg.text,
                        isCreateAccountButtonEnabled = msg.isCreateAccountButtonEnabled
                    )
                }
            }
        }
    }
}
