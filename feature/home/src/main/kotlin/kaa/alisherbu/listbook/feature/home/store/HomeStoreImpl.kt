package kaa.alisherbu.listbook.feature.home.store

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import kaa.alisherbu.listbook.feature.home.domain.model.AudioBook
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
    initialState = HomeState(audioBooks),
    bootstrapper = SimpleBootstrapper(Action.LoadAudioBooks)
)

private val audioBooks = listOf(
    AudioBook(id = "1", name = "Book 1"),
    AudioBook(id = "2", name = "Book 2"),
    AudioBook(id = "3", name = "Book 3"),
    AudioBook(id = "4", name = "Book 4"),
    AudioBook(id = "5", name = "Book 5"),
)