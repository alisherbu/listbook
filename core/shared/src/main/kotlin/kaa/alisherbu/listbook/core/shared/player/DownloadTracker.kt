package kaa.alisherbu.listbook.core.shared.player

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.media3.common.C
import androidx.media3.common.DrmInitData
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
import com.google.common.base.Preconditions
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
    private var startDownloadDialogHelper: StartDownloadDialogHelper? = null

    init {
        this.context = context.applicationContext
        listeners = CopyOnWriteArraySet()
        downloads = HashMap()
        downloadIndex = downloadManager.downloadIndex
        downloadManager.addListener(DownloadManagerListener())
        loadDownloads()
    }

    fun addListener(listener: Listener?) {
        listeners.add(Preconditions.checkNotNull(listener))
    }

    fun removeListener(listener: Listener) {
        listeners.remove(listener)
    }

    fun isDownloaded(mediaItem: MediaItem): Boolean {
        val download = downloads[Preconditions.checkNotNull(mediaItem.localConfiguration).uri]
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
        val download = downloads[Preconditions.checkNotNull(mediaItem.localConfiguration).uri]
        if (download != null && download.state != Download.STATE_FAILED) {
            DownloadService.sendRemoveDownload(
                context,
                AudioDownloadService::class.java,
                download.request.id, /* foreground= */
                false,
            )
        } else {
            if (startDownloadDialogHelper != null) {
                startDownloadDialogHelper!!.release()
            }
            startDownloadDialogHelper = StartDownloadDialogHelper(
                DownloadHelper.forMediaItem(
                    context,
                    mediaItem,
                ),
                mediaItem,
            )
        }
    }

    private fun loadDownloads() {
        try {
            downloadIndex.getDownloads().use { loadedDownloads ->
                while (loadedDownloads.moveToNext()) {
                    val download = loadedDownloads.download
                    downloads[download.request.uri] = download
                }
            }
        } catch (e: IOException) {
            Log.w(TAG, "Failed to query downloads", e)
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

    private inner class StartDownloadDialogHelper(
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

            // The content is DRM protected. We need to acquire an offline license.
            if (Util.SDK_INT < 18) {
                Toast.makeText(
                    context,
                    "R.string.error_drm_unsupported_before_api_18",
                    Toast.LENGTH_LONG,
                )
                    .show()
                Log.e(
                    TAG,
                    "Downloading DRM protected content is not supported on API versions below 18",
                )
                return
            }
            // TODO(internal b/163107948): Support cases where DrmInitData are not in the manifest.
            if (!hasNonNullWidevineSchemaData(format.drmInitData)) {
                Toast.makeText(
                    context,
                    "R.string.download_start_error_offline_license",
                    Toast.LENGTH_LONG,
                )
                    .show()
                Log.e(
                    TAG,
                    "Downloading content where DRM scheme data is not located in the manifest is not" +
                        " supported",
                )
                return
            }
        }

        override fun onPrepareError(helper: DownloadHelper, e: IOException) {
            val isLiveContent = e is LiveContentUnsupportedException
            val toastStringId =
                if (isLiveContent) "R.string.download_live_unsupported" else "R.string.download_start_error"
            val logMessage =
                if (isLiveContent) "Downloading live content unsupported" else "Failed to start download"
            Toast.makeText(context, toastStringId, Toast.LENGTH_LONG).show()
            Log.e(TAG, logMessage, e)
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
            val tracks = downloadHelper.getTracks( /* periodIndex= */0)
            Log.d(TAG, "No dialog content. Downloading entire stream.")
            startDownload()
            downloadHelper.release()
            return
        }

        /**
         * Returns whether any [DrmInitData.SchemeData] that [ ][DrmInitData.SchemeData.matches] [C.WIDEVINE_UUID] has non-null [ ][DrmInitData.SchemeData.data].
         */
        private fun hasNonNullWidevineSchemaData(drmInitData: DrmInitData?): Boolean {
            for (i in 0 until drmInitData!!.schemeDataCount) {
                val schemeData = drmInitData[i]
                if (schemeData.matches(C.WIDEVINE_UUID) && schemeData.hasData()) {
                    return true
                }
            }
            return false
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
                    Util.getUtf8Bytes(Preconditions.checkNotNull(mediaItem.mediaMetadata.title.toString())),
                )
                .copyWithKeySetId(keySetId)
        }
    }

    companion object {
        private const val TAG = "DownloadTracker"
    }
}
