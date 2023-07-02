package kaa.alisherbu.listbook.common.auth.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor

internal class AuthStoreProvider(
    private val storeFactory: StoreFactory,
) {

    fun provide(): AuthStore =
        object :
            AuthStore,
            Store<Intent, AuthState, Label> by storeFactory.create(
                name = "AuthStore",
                executorFactory = ::AuthExecutor,
                reducer = AuthReducer,
                initialState = AuthState()
            ) {}

    private inner class AuthExecutor :
        CoroutineExecutor<Intent, Unit, AuthState, Message, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> AuthState) {
            /* no-op */
        }
    }

    internal object AuthReducer : Reducer<AuthState, Message> {
        override fun AuthState.reduce(msg: Message): AuthState {
            return this
        }
    }
}
