package kaa.alisherbu.feature.player.store

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import javax.inject.Inject
import javax.inject.Provider

internal class PlayerStoreImpl @Inject constructor(
    storeFactory: StoreFactory,
    executorProvider: Provider<PlayerExecutor>,
    reducer: PlayerReducer,
) : PlayerStore,
    Store<Intent, PlayerState, Label> by storeFactory.create(
        name = PlayerStore::class.simpleName,
        executorFactory = executorProvider::get,
        initialState = PlayerState(),
        reducer = reducer,
    )
