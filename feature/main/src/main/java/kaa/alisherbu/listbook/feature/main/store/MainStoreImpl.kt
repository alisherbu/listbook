package kaa.alisherbu.listbook.feature.main.store

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import javax.inject.Inject
import javax.inject.Provider

internal class MainStoreImpl @Inject constructor(
    storeFactory: StoreFactory,
    executorProvider: Provider<MainExecutor>,
    reducer: MainReducer,
) : MainStore,
    Store<Intent, MainState, Label> by storeFactory.create(
        name = MainStore::class.simpleName,
        initialState = MainState(),
        executorFactory = executorProvider::get,
        reducer = reducer,
        bootstrapper = SimpleBootstrapper(Action.Init)
    )
