package kaa.alisherbu.listbook.di

import android.content.Context
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.logging.logger.DefaultLogFormatter
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
import kaa.alisherbu.player.AudioPlayer
import kaa.alisherbu.player.DownloadUtil
import kaa.alisherbu.listbook.coroutine.DefaultAppDispatchers
import javax.inject.Singleton

@Module
class AppModule(private val applicationContext: Context) {
    @Provides
    fun provideStoreFactory(): StoreFactory {
        return LoggingStoreFactory(
            delegate = DefaultStoreFactory(),
            logFormatter = DefaultLogFormatter(LOG_LENGTH)
        )
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
    @UnstableApi
    fun provideExoPlayer(context: Context): ExoPlayer {
        val cacheDataSourceFactory = CacheDataSource.Factory()
            .setCache(DownloadUtil.getDownloadCache(context))
            .setUpstreamDataSourceFactory(DownloadUtil.getHttpDataSourceFactory())
            .setCacheWriteDataSinkFactory(null)
        val mediaSourceFactory = DefaultMediaSourceFactory(context).setDataSourceFactory(cacheDataSourceFactory)
        return ExoPlayer.Builder(context)
            .setMediaSourceFactory(mediaSourceFactory)
            .build()
    }

    @Provides
    @Singleton
    @UnstableApi
    fun provideAudioPlayer(
        exoPlayer: ExoPlayer,
        dispatchers: AppDispatchers,
        context: Context
    ): AudioPlayer {
        return AudioPlayer(exoPlayer, context, dispatchers)
    }

    companion object {
        private const val LOG_LENGTH = 512
    }
}