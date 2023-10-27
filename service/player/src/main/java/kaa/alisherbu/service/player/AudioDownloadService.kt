package kaa.alisherbu.service.player

import android.annotation.SuppressLint
import android.app.Notification
import android.content.Context
import androidx.media3.common.util.NotificationUtil
import androidx.media3.common.util.UnstableApi
import androidx.media3.common.util.Util
import androidx.media3.exoplayer.offline.Download
import androidx.media3.exoplayer.offline.DownloadManager
import androidx.media3.exoplayer.offline.DownloadNotificationHelper
import androidx.media3.exoplayer.offline.DownloadService
import androidx.media3.exoplayer.scheduler.PlatformScheduler
import androidx.media3.exoplayer.scheduler.Scheduler
import kaa.alisherbu.listbook.core.shared.R


@UnstableApi
class AudioDownloadService : DownloadService(
    FOREGROUND_NOTIFICATION_ID,
    DEFAULT_FOREGROUND_NOTIFICATION_UPDATE_INTERVAL,
    DownloadUtil.DOWNLOAD_NOTIFICATION_CHANNEL_ID,
    R.string.download_channel_name,
    R.string.download_channel_description
) {
    override fun getDownloadManager(): DownloadManager {
        val downloadManager = DownloadUtil.getDownloadManager(this)
        val terminalStateNotificationHelper = TerminalStateNotificationHelper(
            context = this,
            downloadNotificationHelper = DownloadUtil.getDownloadNotificationHelper(this),
            nextNotificationId = FOREGROUND_NOTIFICATION_ID + 1
        )
        downloadManager.addListener(terminalStateNotificationHelper)
        return downloadManager
    }

    @SuppressLint("MissingPermission")
    override fun getScheduler(): Scheduler {
        return PlatformScheduler(this, JOB_ID)
    }

    override fun getForegroundNotification(
        downloads: MutableList<Download>,
        notMetRequirements: Int
    ): Notification {
        return DownloadUtil.getDownloadNotificationHelper(this)
            .buildProgressNotification(
                /* context = */ this,
                /* smallIcon = */ R.drawable.ic_download_24,
                /* contentIntent = */ null,
                /* message = */ null,
                /* downloads = */ downloads,
                /* notMetRequirements = */ notMetRequirements
            )
    }

    class TerminalStateNotificationHelper(
        private val context: Context,
        private val downloadNotificationHelper: DownloadNotificationHelper,
        private var nextNotificationId: Int
    ) : DownloadManager.Listener {
        override fun onDownloadChanged(
            downloadManager: DownloadManager,
            download: Download,
            finalException: Exception?
        ) {
            val notification: Notification = when (download.state) {
                Download.STATE_COMPLETED -> {
                    downloadNotificationHelper.buildDownloadCompletedNotification(
                        context,
                        R.drawable.ic_download_done_24,  /* contentIntent= */
                        null,
                        Util.fromUtf8Bytes(download.request.data)
                    )
                }

                Download.STATE_FAILED -> {
                    downloadNotificationHelper.buildDownloadFailedNotification(
                        context,
                        R.drawable.ic_download_done_24,  /* contentIntent= */
                        null,
                        Util.fromUtf8Bytes(download.request.data)
                    )
                }

                else -> {
                    return
                }
            }
            NotificationUtil.setNotification(context, nextNotificationId++, notification)
        }
    }

    companion object {
        private const val FOREGROUND_NOTIFICATION_ID = 1
        private const val JOB_ID = 1
    }
}