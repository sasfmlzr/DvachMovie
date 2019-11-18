package dvachmovie.service

import android.app.DownloadManager
import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import dvachmovie.di.core.Injector

class DownloadService : IntentService(Context.DOWNLOAD_SERVICE) {

    companion object {
        private const val DOWNLOAD_PATH = "dvachmovie.service_DownloadService_Download_path"
        private const val DESTINATION_PATH = "dvachmovie.service_DownloadService_Destination_path"
        private const val COOKIE = "dvachmovie.service_DownloadService_Cookie"

        fun getDownloadService(@NonNull callingClassContext: Context,
                               @NonNull downloadPath: String,
                               @NonNull destinationPath: String,
                               @NonNull cookie: String): Intent {
            return Intent(callingClassContext, DownloadService::class.java)
                    .putExtra(DOWNLOAD_PATH, downloadPath)
                    .putExtra(DESTINATION_PATH, destinationPath)
                    .putExtra(COOKIE, cookie)
        }
    }

    override fun onCreate() {
        super.onCreate()
        Injector.serviceComponent().inject(this)
    }

    override fun onHandleIntent(@Nullable intent: Intent?) {
        val downloadPath = intent!!.getStringExtra(DOWNLOAD_PATH)
        val destinationPath = intent.getStringExtra(DESTINATION_PATH)
        val cookie = intent.getStringExtra(COOKIE)
        startDownload(downloadPath, destinationPath, cookie)
    }

    private fun startDownload(downloadPath: String?, destinationPath: String?, cookie: String?) {
        val uri = Uri.parse(downloadPath)
        val request = DownloadManager.Request(uri)
                .addRequestHeader("Cookie", cookie)
                .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or
                        DownloadManager.Request.NETWORK_WIFI)
                .setNotificationVisibility(
                        DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setTitle(uri.pathSegments.last())
                .setDestinationInExternalPublicDir(destinationPath, uri.lastPathSegment)
        (getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager).enqueue(request)
    }
}
