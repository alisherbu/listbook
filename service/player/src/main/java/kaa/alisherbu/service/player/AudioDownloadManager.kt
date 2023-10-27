package kaa.alisherbu.service.player

import android.annotation.SuppressLint
import androidx.media3.exoplayer.offline.Download
import androidx.media3.exoplayer.offline.DownloadIndex
import androidx.media3.exoplayer.offline.DownloadManager
import javax.inject.Inject

@SuppressLint("UnsafeOptInUsageError")
class AudioDownloadManager @Inject constructor(
    downloadManager: DownloadManager
) {
    private val downloads: MutableMap<String, Download> = hashMapOf()
    private val downloadIndex: DownloadIndex = downloadManager.downloadIndex
    private val downloadListener = object : DownloadManager.Listener {
        override fun onDownloadChanged(
            downloadManager: DownloadManager,
            download: Download,
            finalException: Exception?
        ) {
            if (download.state == Download.STATE_COMPLETED) {
                downloads[download.request.id] = download
            }
        }

        override fun onDownloadRemoved(downloadManager: DownloadManager, download: Download) {
            downloads.remove(download.request.id)
        }
    }

    init {
        loadDownloads()
        downloadManager.addListener(downloadListener)
    }

    fun isDownloaded(id: String): Boolean {
        val download = downloads[id]
        return download != null && download.state != Download.STATE_FAILED
    }

    internal fun getDownload(id: String): Download? {
        return downloads[id]
    }

    internal fun getDownloads(): List<Download> {
        return downloads.values.toList()
    }

    private fun loadDownloads() {
        downloadIndex.getDownloads().use { cursor ->
            while (cursor.moveToNext()) {
                val download = cursor.download
                downloads[download.request.id] = download
            }
        }
    }
}