package kaa.alisherbu.listbook.chapter.store

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import javax.inject.Inject
import javax.inject.Provider

internal class ChapterStoreImpl @Inject constructor(
    storeFactory: StoreFactory,
    executorProvider: Provider<ChapterExecutor>,
    reducer: ChapterReducer
) : ChapterStore, Store<Intent, ChapterState, Label> by storeFactory.create(
    name = ChapterStore::class.simpleName,
    initialState = ChapterState(),
    executorFactory = executorProvider::get,
    reducer = reducer,
)