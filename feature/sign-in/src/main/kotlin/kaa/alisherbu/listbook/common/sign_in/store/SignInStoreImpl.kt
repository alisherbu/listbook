package kaa.alisherbu.listbook.common.sign_in.store

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import javax.inject.Inject
import javax.inject.Provider

internal class SignInStoreImpl @Inject constructor(
    storeFactory: StoreFactory,
    executorProvider: Provider<SignInExecutor>,
    reducer: SignInReducer
) : SignInStore, Store<Intent, SignInState, Label> by storeFactory.create(
    name = "SignInStore",
    initialState = SignInState(),
    executorFactory = { executorProvider.get() },
    reducer = reducer
)