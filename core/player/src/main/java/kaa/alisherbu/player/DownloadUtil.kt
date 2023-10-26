package kaa.alisherbu.player

import android.content.Context
import androidx.media3.common.util.UnstableApi
import androidx.media3.database.DatabaseProvider
import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.cache.Cache
import androidx.media3.datasource.cache.NoOpCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import androidx.media3.exoplayer.offline.DownloadManager
import androidx.media3.exoplayer.offline.DownloadNotificationHelper
import java.io.File
import java.net.CookieHandler
import java.net.CookieManager
import java.net.CookiePolicy
import java.util.concurrent.Executor

@UnstableApi
object DownloadUtil {
    private const val DOWNLOAD_CONTENT_DIRECTORY = "downloads"
    const val DOWNLOAD_NOTIFICATION_CHANNEL_ID = "download_channel"
    private lateinit var downloadManager: DownloadManager
    private lateinit var databaseProvider: DatabaseProvider
    private lateinit var downloadCache: Cache
    private lateinit var downloadDirectory: File
    private lateinit var httpDataSourceFactory: DataSource.Factory
    private lateinit var downloadNotificationHelper: DownloadNotificationHelper
    private val downloadExecutor = Executor(Runnable::run)

    fun getDownloadManager(context: Context): DownloadManager {
        ensureDownloadManagerInitialized(context)
        return downloadManager
    }

    private fun ensureDownloadManagerInitialized(context: Context) {
        if (!this::downloadManager.isInitialized) {
            downloadManager = DownloadManager(
                context,
                getDatabaseProvider(context),
                getDownloadCache(context),
                getHttpDataSourceFactory(),
                downloadExecutor
            )
            downloadManager.maxParallelDownloads = 5
        }
    }

    private fun getDatabaseProvider(context: Context): DatabaseProvider {
        if (!this::databaseProvider.isInitialized) {
            databaseProvider = StandaloneDatabaseProvider(context)
        }
        return databaseProvider
    }

    fun getDownloadCache(context: Context): Cache {
        if (!this::downloadCache.isInitialized) {
            val downloadDirectory = getDownloadDirectory(context)
            val downloadContentDirectory = File(downloadDirectory, DOWNLOAD_CONTENT_DIRECTORY)
            val databaseProvider = getDatabaseProvider(context)
            val evictor = NoOpCacheEvictor()
            downloadCache = SimpleCache(downloadContentDirectory, evictor, databaseProvider)
        }
        return downloadCache
    }

    fun getHttpDataSourceFactory(): DataSource.Factory {
        if (!this::httpDataSourceFactory.isInitialized) {
            val cookieManager = CookieManager()
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER)
            CookieHandler.setDefault(cookieManager)
            httpDataSourceFactory = DefaultHttpDataSource.Factory()
        }
        return httpDataSourceFactory
    }

    private fun getDownloadDirectory(context: Context): File {
        if (!this::downloadDirectory.isInitialized) {
            downloadDirectory = context.getExternalFilesDir(null) ?: context.filesDir
        }
        return downloadDirectory
    }

    fun getDownloadNotificationHelper(context: Context): DownloadNotificationHelper {
        if (!this::downloadNotificationHelper.isInitialized) {
            downloadNotificationHelper = DownloadNotificationHelper(
                context,
                DOWNLOAD_NOTIFICATION_CHANNEL_ID,
            )
        }
        return downloadNotificationHelper
    }
}
