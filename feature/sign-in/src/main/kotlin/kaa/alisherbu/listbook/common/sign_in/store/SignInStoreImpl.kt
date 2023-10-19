package kaa.alisherbu.listbook.common.sign_in.store

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import javax.inject.Inject

internal class SignInStoreImpl @Inject constructor(
    storeFactory: StoreFactory,
    executor: SignInExecutor,
    reducer: SignInReducer
) : SignInStore, Store<Intent, SignInState, Label> by storeFactory.create(
    name = "SignInStore",
    initialState = SignInState(),
    executorFactory = { executor },
    reducer = reducer
)