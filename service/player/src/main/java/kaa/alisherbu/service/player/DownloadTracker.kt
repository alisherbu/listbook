package kaa.alisherbu.service.player

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.offline.Download
import androidx.media3.exoplayer.offline.DownloadHelper
import androidx.media3.exoplayer.offline.DownloadIndex
import androidx.media3.exoplayer.offline.DownloadManager
import androidx.media3.exoplayer.offline.DownloadRequest
import androidx.media3.exoplayer.offline.DownloadService
import java.io.IOException
import java.util.concurrent.CopyOnWriteArraySet

/**
 * Tracks media that has been downloaded.
 */
@UnstableApi
class DownloadTracker(
    private val context: Context,
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

    private val listeners: CopyOnWriteArraySet<Listener> = CopyOnWriteArraySet()
    private val downloads: HashMap<String, Download> = HashMap()
    private val downloadIndex: DownloadIndex = downloadManager.downloadIndex
    private var startDownloadDialogHelper: StartDownloadHelper? = null

    init {
        downloadManager.addListener(DownloadManagerListener())
        loadDownloads()
    }

    fun addListener(listener: Listener) {
        listeners.add(listener)
    }

    fun removeListener(listener: Listener) {
        listeners.remove(listener)
    }

    fun isDownloaded(id: String): Boolean {
        val download = downloads[id]
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
                download.request.id,
                /* foreground= */ false,
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
        downloadIndex.getDownloads().use { cursor ->
            while (cursor.moveToNext()) {
                val download = cursor.download
                downloads[download.request.id] = download
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
            listeners.forEach(Listener::onDownloadsChanged)
        }

        override fun onDownloadRemoved(downloadManager: DownloadManager, download: Download) {
            downloads.remove(download.request.id)
            listeners.forEach(Listener::onDownloadsChanged)
        }
    }

    private inner class StartDownloadHelper(
        private val downloadHelper: DownloadHelper,
        private val mediaItem: MediaItem
    ) : DownloadHelper.Callback {

        init {
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
            helper.release()
        }

        override fun onPrepareError(helper: DownloadHelper, e: IOException) {
            /* no-op */
        }

        private fun buildDownloadRequest(): DownloadRequest {
            return downloadHelper.getDownloadRequest(mediaItem.mediaId, null)
        }
    }
}
