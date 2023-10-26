package kaa.alisherbu.listbook.feature.signup.store

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import javax.inject.Inject
import javax.inject.Provider

internal class SignupStoreImpl @Inject constructor(
    storeFactory: StoreFactory,
    executorProvider: Provider<SignupExecutor>,
    reducer: SignupReducer,
) : SignupStore,
    Store<Intent, SignupState, Label> by storeFactory.create(
        name = SignupStore::class.simpleName,
        executorFactory = executorProvider::get,
        reducer = reducer,
        initialState = SignupState(),
    )
