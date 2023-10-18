package kaa.alisherbu.listbook.di

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import dagger.Module
import dagger.Provides

@Module
class MainModule {
    @Provides
    fun provideStoreFactory(): StoreFactory {
        return LoggingStoreFactory(DefaultStoreFactory())
    }
}