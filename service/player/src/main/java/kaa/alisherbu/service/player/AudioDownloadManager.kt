package kaa.alisherbu.service.player

import android.annotation.SuppressLint
import androidx.media3.exoplayer.offline.Download
import androidx.media3.exoplayer.offline.DownloadIndex
import androidx.media3.exoplayer.offline.DownloadManager
import java.util.concurrent.CopyOnWriteArrayList
import javax.inject.Inject

@SuppressLint("UnsafeOptInUsageError")
class AudioDownloadManager @Inject constructor(
    downloadManager: DownloadManager
) {
    private val downloads: MutableMap<String, Download> = hashMapOf()
    private val downloadIndex: DownloadIndex = downloadManager.downloadIndex
    private val listeners = CopyOnWriteArrayList<Listener>()
    private val downloadListener = object : DownloadManager.Listener {
        override fun onDownloadChanged(
            downloadManager: DownloadManager,
            download: Download,
            finalException: Exception?
        ) {
            if (download.state == Download.STATE_COMPLETED) {
                downloads[download.request.id] = download
                listeners.forEach { it.onDownloadCompleted(download.request.id) }
            }
        }

        override fun onDownloadRemoved(downloadManager: DownloadManager, download: Download) {
            downloads.remove(download.request.id)
            listeners.forEach { it.onDownloadRemoved(download.request.id) }
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

    fun addListener(listener: Listener) {
        listeners.add(listener)
    }

    fun removeListener(listener: Listener) {
        listeners.remove(listener)
    }

    interface Listener {
        fun onDownloadCompleted(id: String)
        fun onDownloadRemoved(id: String)
    }
}
