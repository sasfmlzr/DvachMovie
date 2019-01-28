package dvachmovie.service

import android.app.DownloadManager
import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.annotation.NonNull
import androidx.annotation.Nullable


class DownloadService : IntentService(Context.DOWNLOAD_SERVICE) {

    companion object {
        private const val DOWNLOAD_PATH = "dvachmovie.service_DownloadService_Download_path"
        private const val DESTINATION_PATH = "dvachmovie.service_DownloadService_Destination_path"

        fun getDownloadService(@NonNull callingClassContext: Context, @NonNull downloadPath: String, @NonNull destinationPath: String): Intent {
            return Intent(callingClassContext, DownloadService::class.java)
                    .putExtra(DOWNLOAD_PATH, downloadPath)
                    .putExtra(DESTINATION_PATH, destinationPath)
        }
    }

    override fun onHandleIntent(@Nullable intent: Intent?) {
        val downloadPath = intent!!.getStringExtra(DOWNLOAD_PATH)
        val destinationPath = intent.getStringExtra(DESTINATION_PATH)
        startDownload(downloadPath, destinationPath)
    }
    private fun startDownload(downloadPath: String, destinationPath: String) {
        val uri = Uri.parse(downloadPath) // Path where you want to download file.
        val request = DownloadManager.Request(uri)
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)  // Tell on which network you want to download file.
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)  // This will show notification on top when downloading the file.
        request.setTitle(uri.pathSegments.last()) // Title for notification.
        request.setVisibleInDownloadsUi(true)
        request.setDestinationInExternalPublicDir(destinationPath, uri.lastPathSegment)  // Storage directory path
        (getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager).enqueue(request) // This will start downloading
    }
}
