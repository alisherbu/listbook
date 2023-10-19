package kaa.alisherbu.listbook.di

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides

@Module
class MainModule {
    @Provides
    fun provideStoreFactory(): StoreFactory {
        return LoggingStoreFactory(DefaultStoreFactory())
    }

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return Firebase.auth
    }
}