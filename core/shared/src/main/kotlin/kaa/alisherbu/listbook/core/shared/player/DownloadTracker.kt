package kaa.alisherbu.listbook.core.shared.player

import android.content.Context
import android.net.Uri
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

    private val context: Context
    private val listeners: CopyOnWriteArraySet<Listener>
    private val downloads: HashMap<Uri, Download>
    private val downloadIndex: DownloadIndex
    private var startDownloadDialogHelper: StartDownloadHelper? = null

    init {
        this.context = context.applicationContext
        listeners = CopyOnWriteArraySet()
        downloads = HashMap()
        downloadIndex = downloadManager.downloadIndex
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
        val download = downloads[checkNotNull(mediaItem.localConfiguration).uri]
        return download != null && download.state != Download.STATE_FAILED
    }

    fun getDownloadRequest(uri: Uri): DownloadRequest? {
        val download = downloads[uri]
        return if (download != null && download.state != Download.STATE_FAILED) download.request else null
    }

    fun removeDownload(mediaItem: MediaItem) {
        val download = downloads[mediaItem.localConfiguration?.uri]
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

    fun loadDownloads() {
        val list = mutableListOf<Download>()
        downloadIndex.getDownloads().use { cursor ->
            while (cursor.moveToNext()) {
                val download = cursor.download
                downloads[download.request.uri] = download
                list.add(download)
            }
        }
        Timber.d(
            "count = ${list.size}, downloads = ${
                list.map {
                    Pair(
                        it.request.id,
                        it.request.uri,
                    )
                }
            }",
        )
    }

    private inner class DownloadManagerListener : DownloadManager.Listener {
        override fun onDownloadChanged(
            downloadManager: DownloadManager,
            download: Download,
            finalException: Exception?
        ) {
            downloads[download.request.uri] = download
            for (listener in listeners) {
                listener.onDownloadsChanged()
            }
        }

        override fun onDownloadRemoved(downloadManager: DownloadManager, download: Download) {
            downloads.remove(download.request.uri)
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
