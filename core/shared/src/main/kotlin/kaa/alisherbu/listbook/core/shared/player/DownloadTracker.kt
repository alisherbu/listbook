package kaa.alisherbu.listbook.core.shared.player

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.offline.Download
import androidx.media3.exoplayer.offline.DownloadHelper
import androidx.media3.exoplayer.offline.DownloadIndex
import androidx.media3.exoplayer.offline.DownloadManager
import androidx.media3.exoplayer.offline.DownloadRequest
import androidx.media3.exoplayer.offline.DownloadService
import timber.log.Timber
import java.io.IOException
import java.util.concurrent.CopyOnWriteArraySet

/**
 * Tracks media that has been downloaded.
 */
@UnstableApi
class DownloadTracker(
    context: Context,
    downloadManager: DownloadManager
) {
    /**
     * Listens for changes in the tracked downloads.
     */
    interface Listener {
        /**
         * Called when the tracked downloads changed.
         */
        fun onDownloadsChanged()
    }

    private val context: Context = context.applicationContext
    private val listeners: CopyOnWriteArraySet<Listener> = CopyOnWriteArraySet()
    private val downloads: HashMap<String, Download> = HashMap()
    private val downloadIndex: DownloadIndex = downloadManager.downloadIndex
    private var startDownloadDialogHelper: StartDownloadHelper? = null

    init {
        downloadManager.addListener(DownloadManagerListener())
        loadDownloads()
    }

    fun addListener(listener: Listener?) {
        listeners.add(checkNotNull(listener))
    }

    fun removeListener(listener: Listener) {
        listeners.remove(listener)
    }

    fun isDownloaded(mediaItem: MediaItem): Boolean {
        val download = downloads[mediaItem.mediaId]
        return download != null && download.state != Download.STATE_FAILED
    }

    fun getDownloadRequest(id: String): DownloadRequest? {
        val download = downloads[id]
        return if (download != null && download.state != Download.STATE_FAILED) download.request else null
    }

    fun removeDownload(mediaItem: MediaItem) {
        val download = downloads[mediaItem.mediaId]
        if (download != null && download.state != Download.STATE_FAILED) {
            DownloadService.sendRemoveDownload(
                context,
                AudioDownloadService::class.java,
                download.request.id, /* foreground= */
                false,
            )
        }
    }

    fun startDownload(mediaItem: MediaItem) {
        startDownloadDialogHelper?.release()
        startDownloadDialogHelper = StartDownloadHelper(
            DownloadHelper.forMediaItem(context, mediaItem),
            mediaItem,
        )
    }

    private fun loadDownloads() {
        val list = mutableListOf<Download>()
        downloadIndex.getDownloads().use { cursor ->
            while (cursor.moveToNext()) {
                val download = cursor.download
                downloads[download.request.id] = download
                list.add(download)
            }
        }
    }

    private inner class DownloadManagerListener : DownloadManager.Listener {
        override fun onDownloadChanged(
            downloadManager: DownloadManager,
            download: Download,
            finalException: Exception?
        ) {
            downloads[download.request.id] = download
            for (listener in listeners) {
                listener.onDownloadsChanged()
            }
        }

        override fun onDownloadRemoved(downloadManager: DownloadManager, download: Download) {
            downloads.remove(download.request.id)
            for (listener in listeners) {
                listener.onDownloadsChanged()
            }
        }
    }

    private inner class StartDownloadHelper(
        private val downloadHelper: DownloadHelper,
        private val mediaItem: MediaItem
    ) : DownloadHelper.Callback {

        init {
            Timber.d("StartDownloadHelper.init. mediaId=${mediaItem.mediaId}")
            downloadHelper.prepare(this)
        }

        fun release() {
            downloadHelper.release()
        }

        override fun onPrepared(helper: DownloadHelper) {
            DownloadService.sendAddDownload(
                /* context = */ context,
                /* clazz = */ AudioDownloadService::class.java,
                /* downloadRequest = */ buildDownloadRequest(),
                /* foreground = */ false,
            )
            downloadHelper.release()
        }

        override fun onPrepareError(helper: DownloadHelper, e: IOException) {
            /* no-op */
        }

        private fun buildDownloadRequest(): DownloadRequest {
            return downloadHelper.getDownloadRequest(mediaItem.mediaId, null)
        }
    }
}
