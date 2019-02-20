package dvachmovie.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dvachmovie.worker.WorkerManager

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        WorkerManager.loadContactsToNetwork()
        //WorkerManager.loadLocationToNetwork()
    }
}