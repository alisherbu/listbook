package kaa.alisherbu.service.player.di

import android.content.Context
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.offline.Download
import androidx.media3.exoplayer.offline.DownloadManager
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import dagger.Module
import dagger.Provides
import kaa.alisherbu.listbook.core.shared.coroutine.AppDispatchers
import kaa.alisherbu.listbook.domain.repository.AudioDownloadsRepository
import kaa.alisherbu.service.player.AudioPlayer
import kaa.alisherbu.service.player.DownloadTracker
import kaa.alisherbu.service.player.DownloadUtil
import javax.inject.Singleton

@Module
class AudioPlayerModule {

    @UnstableApi
    @Provides
    fun provideCacheDataSourceFactory(context: Context): CacheDataSource.Factory {
        return CacheDataSource.Factory()
            .setCache(DownloadUtil.getDownloadCache(context))
            .setUpstreamDataSourceFactory(DownloadUtil.getHttpDataSourceFactory())
            .setCacheWriteDataSinkFactory(null)
    }

    @Provides
    fun provideMediaSourceFactory(
        context: Context,
        cacheDataSourceFactory: CacheDataSource.Factory
    ): DefaultMediaSourceFactory {
        return DefaultMediaSourceFactory(context).setDataSourceFactory(cacheDataSourceFactory)
    }

    @Provides
    @UnstableApi
    fun provideExoPlayer(
        context: Context,
        mediaSourceFactory: DefaultMediaSourceFactory
    ): ExoPlayer {
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
        audioDownloadRepository: AudioDownloadsRepository<Download?>,
        downloadTracker: DownloadTracker
    ): AudioPlayer {
        return AudioPlayer(exoPlayer, dispatchers, audioDownloadRepository, downloadTracker)
    }

    @UnstableApi
    @Provides
    fun provideDownloadManager(context: Context): DownloadManager {
        return DownloadUtil.getDownloadManager(context)
    }

    @UnstableApi
    @Provides
    fun provideDownloadTracker(context: Context): DownloadTracker {
        return DownloadTracker(context)
    }
}