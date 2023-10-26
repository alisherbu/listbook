package kaa.alisherbu.listbook.core.shared.player

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.media3.common.Format
import androidx.media3.common.MediaItem
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.media3.common.util.Util
import androidx.media3.exoplayer.offline.Download
import androidx.media3.exoplayer.offline.DownloadHelper
import androidx.media3.exoplayer.offline.DownloadHelper.LiveContentUnsupportedException
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

    fun getDownloads(): List<Download> {
        return downloads.values.toList()
    }

    fun toggleDownload(
        mediaItem: MediaItem
    ) {
        val download = downloads[mediaItem.localConfiguration?.uri]
        if (download != null && download.state != Download.STATE_FAILED) {
            DownloadService.sendRemoveDownload(
                context,
                AudioDownloadService::class.java,
                download.request.id, /* foreground= */
                false,
            )
        } else {
            startDownloadDialogHelper?.release()
            startDownloadDialogHelper = StartDownloadHelper(
                DownloadHelper.forMediaItem(
                    context,
                    mediaItem,
                ),
                mediaItem,
            )
        }
    }

    private fun loadDownloads() {
        downloadIndex.getDownloads().use { cursor ->
            while (cursor.moveToNext()) {
                val download = cursor.download
                downloads[download.request.uri] = download
            }
        }
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
        private var keySetId: ByteArray? = null

        init {
            downloadHelper.prepare(this)
        }

        fun release() {
            downloadHelper.release()
        }

        // DownloadHelper.Callback implementation.
        override fun onPrepared(helper: DownloadHelper) {
            val format = getFirstFormatWithDrmInitData(helper)
            if (format == null) {
                onDownloadPrepared(helper)
                return
            }
        }

        override fun onPrepareError(helper: DownloadHelper, e: IOException) {

        }

        // Internal methods.
        /**
         * Returns the first [Format] with a non-null [Format.drmInitData] found in the
         * content's tracks, or null if none is found.
         */
        private fun getFirstFormatWithDrmInitData(helper: DownloadHelper): Format? {
            for (periodIndex in 0 until helper.periodCount) {
                val mappedTrackInfo = helper.getMappedTrackInfo(periodIndex)
                for (rendererIndex in 0 until mappedTrackInfo.rendererCount) {
                    val trackGroups = mappedTrackInfo.getTrackGroups(rendererIndex)
                    for (trackGroupIndex in 0 until trackGroups.length) {
                        val trackGroup = trackGroups[trackGroupIndex]
                        for (formatIndex in 0 until trackGroup.length) {
                            val format = trackGroup.getFormat(formatIndex)
                            if (format.drmInitData != null) {
                                return format
                            }
                        }
                    }
                }
            }
            return null
        }

        private fun onDownloadPrepared(helper: DownloadHelper) {
            if (helper.periodCount == 0) {
                Log.d(TAG, "No periods found. Downloading entire stream.")
                startDownload()
                downloadHelper.release()
                return
            }
            Log.d(TAG, "No dialog content. Downloading entire stream.")
            startDownload()
            downloadHelper.release()
            return
        }

        private fun startDownload(downloadRequest: DownloadRequest = buildDownloadRequest()) {
            DownloadService.sendAddDownload(
                context,
                AudioDownloadService::class.java,
                downloadRequest, /* foreground= */
                false,
            )
        }

        private fun buildDownloadRequest(): DownloadRequest {
            return downloadHelper
                .getDownloadRequest(
                    Util.getUtf8Bytes(checkNotNull(mediaItem.mediaMetadata.title.toString())),
                )
                .copyWithKeySetId(keySetId)
        }
    }

    companion object {
        private const val TAG = "DownloadTracker"
    }
}
