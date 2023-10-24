package kaa.alisherbu.listbook.di

import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import kaa.alisherbu.listbook.core.shared.coroutine.AppDispatchers
import kaa.alisherbu.listbook.core.shared.player.AudioPlayer
import kaa.alisherbu.listbook.coroutine.DefaultAppDispatchers
import javax.inject.Singleton

@Module
class AppModule(private val applicationContext: Context) {
    @Provides
    fun provideStoreFactory(): StoreFactory {
        return LoggingStoreFactory(DefaultStoreFactory())
    }

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return Firebase.auth
    }

    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return Firebase.firestore
    }

    @Provides
    fun provideAppDispatchers(): AppDispatchers {
        return DefaultAppDispatchers()
    }

    @Provides
    fun provideApplicationContext(): Context {
        return applicationContext
    }

    @Provides
    fun provideExoPlayer(context: Context): ExoPlayer {
        return ExoPlayer.Builder(context).build()
    }

    @Provides
    @Singleton
    fun provideAudioPlayer(exoPlayer: ExoPlayer): AudioPlayer {
        return AudioPlayer(exoPlayer)
    }
}