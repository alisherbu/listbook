package kaa.alisherbu.listbook.feature.home.store

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import javax.inject.Inject
import javax.inject.Provider

internal class HomeStoreImpl @Inject constructor(
    storeFactory: StoreFactory,
    executorProvider: Provider<HomeExecutor>,
    reducer: HomeReducer
) : HomeStore, Store<Intent, HomeState, Label> by storeFactory.create(
    name = HomeStore::class.simpleName,
    executorFactory = executorProvider::get,
    reducer = reducer,
    initialState = HomeState(),
    bootstrapper = SimpleBootstrapper(Action.LoadAudioBooks)
)
