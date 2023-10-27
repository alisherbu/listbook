package kaa.alisherbu.listbook.core.data.repository

import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.offline.Download
import androidx.media3.exoplayer.offline.DownloadIndex
import androidx.media3.exoplayer.offline.DownloadManager
import kaa.alisherbu.listbook.domain.repository.AudioDownloadsRepository
import javax.inject.Inject

@UnstableApi
class AudioDownloadsRepositoryImpl @Inject constructor(
    downloadManager: DownloadManager
) : AudioDownloadsRepository<Download?> {
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

    override fun isDownloaded(id: String): Boolean {
        val download = downloads[id]
        return download != null && download.state != Download.STATE_FAILED
    }

    override fun getDownload(id: String): Download? {
        return downloads[id]
    }

    override fun getDownloads(): List<Download> {
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