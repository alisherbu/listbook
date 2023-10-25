package kaa.alisherbu.listbook.core.shared.player

import android.content.Context
import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.exoplayer.RenderersFactory
import androidx.media3.exoplayer.offline.Download
import androidx.media3.exoplayer.offline.DownloadHelper
import androidx.media3.exoplayer.offline.DownloadIndex
import androidx.media3.exoplayer.offline.DownloadManager
import androidx.media3.exoplayer.offline.DownloadRequest
import androidx.media3.exoplayer.offline.DownloadService
import java.io.IOException
import java.util.concurrent.CopyOnWriteArraySet

@UnstableApi
class DownloadTracker(
    private val context: Context,
    private val dataSourceFactory: DataSource.Factory,
    private val downloadManager: DownloadManager
) {
    private val listeners = CopyOnWriteArraySet<Listener>()
    private val downloads = HashMap<Uri, Download>()
    private val downloadIndex: DownloadIndex = downloadManager.downloadIndex

    init {
        downloadManager.addListener(DownloadManagerListener)
    }

    fun addListener(listener: Listener) {
        listeners.add(listener)
    }

    fun removeListener(listener: Listener) {
        listeners.remove(listener)
    }

    fun isDownloaded(mediaItem: MediaItem): Boolean {
        val download = downloads[mediaItem.localConfiguration?.uri]
        return download != null && download.state != Download.STATE_FAILED
    }

    fun getDownloadRequest(uri: Uri?): DownloadRequest? {
        val download = downloads[uri]
        return if (download != null && download.state != Download.STATE_FAILED) download.request else null
    }

    fun toggleDownload(mediaItem: MediaItem, renderersFactory: RenderersFactory) {
        val download = downloads[mediaItem.localConfiguration?.uri]
        if (download != null && download.state != Download.STATE_FAILED) {
            DownloadService.sendRemoveDownload(
                /* context = */ context,
                /* clazz = */ AudioDownloadService::class.java,
                /* id = */ download.request.id,
                /* foreground = */ false
            )
        } else {

        }
    }

    private class StartDownloadHelper(
        private val downloadHelper: DownloadHelper,
        private val mediaItem: MediaItem
    ):DownloadHelper.Callback {
        override fun onPrepared(helper: DownloadHelper) {

        }

        override fun onPrepareError(helper: DownloadHelper, e: IOException) {

        }

    }

    private object DownloadManagerListener : DownloadManager.Listener {
        override fun onDownloadChanged(
            downloadManager: DownloadManager,
            download: Download,
            finalException: Exception?
        ) {

        }

        override fun onDownloadRemoved(downloadManager: DownloadManager, download: Download) {

        }
    }

    /** Listens for changes in the tracked downloads.  */
    interface Listener {
        /** Called when the tracked downloads changed.  */
        fun onDownloadsChanged()
    }
}