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
    private val executor: SignupExecutor = SignupExecutor()
) {

    fun provide(): SignupStore =
        object :
            SignupStore,
            Store<Intent, SignupState, Label> by storeFactory.create(
                name = "SignupStore",
                executorFactory = { executor },
                reducer = SignReducer,
                initialState = SignupState()
            ) {}

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
