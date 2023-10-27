package kaa.alisherbu.service.player

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.offline.Download
import androidx.media3.exoplayer.offline.DownloadHelper
import androidx.media3.exoplayer.offline.DownloadRequest
import androidx.media3.exoplayer.offline.DownloadService
import java.io.IOException

/**
 * Tracks media that has been downloaded.
 */
@UnstableApi
class DownloadTracker(private val context: Context) {

    private var startDownloadDialogHelper: StartDownloadHelper? = null

    fun startDownload(mediaItem: MediaItem) {
        startDownloadDialogHelper?.release()
        startDownloadDialogHelper = StartDownloadHelper(
            DownloadHelper.forMediaItem(context, mediaItem),
            mediaItem,
        )
    }

    fun removeDownload(id: String) {
        DownloadService.sendRemoveDownload(
            context,
            AudioDownloadService::class.java,
            id,
            /* foreground= */ false,
        )
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
