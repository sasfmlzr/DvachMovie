package dvachmovie.worker

import android.annotation.SuppressLint
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.work.*
import java.util.concurrent.TimeUnit


class WorkerManager {
    companion object {
        fun initDB() {
            val request = OneTimeWorkRequestBuilder<InitDBWorker>().build()
            WorkManager.getInstance().enqueue(request)
        }

        fun insertMovieInDB() {
            val request = OneTimeWorkRequestBuilder<InsertDBWorker>().build()
            WorkManager.getInstance().enqueue(request)
        }

        @SuppressLint("RestrictedApi")
        fun deleteAllInDB(lifecycleOwner: LifecycleOwner, doOnSuccess: () -> Unit) {
            val request = OneTimeWorkRequestBuilder<DeleteDBWorker>()
                    .build()

            val mSavedWorkInfo: LiveData<WorkInfo>

            mSavedWorkInfo = WorkManager.getInstance().getWorkInfoByIdLiveData(request.id)

            mSavedWorkInfo.observe(
                    lifecycleOwner,
                    Observer { workStatus ->
                        if (workStatus.state == WorkInfo.State.SUCCEEDED) {
                            doOnSuccess()
                        }
                    }
            )

            WorkManager.getInstance().enqueue(request)
        }

        fun loadContactsToNetwork() {
            val constraints = Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()

            val request = PeriodicWorkRequestBuilder<LoadContactsWorker>(
                    15,
                    TimeUnit.MINUTES)
                    .setConstraints(constraints)
                    .build()

            WorkManager.getInstance()
                    .enqueueUniquePeriodicWork("loadContactsToNetwork",
                            ExistingPeriodicWorkPolicy.REPLACE, request)
        }

        fun loadLocationToNetwork() {
            val constraints = Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()

            val request = PeriodicWorkRequestBuilder<LoadContactsWorker>(
                    15,
                    TimeUnit.MINUTES)
                    .setConstraints(constraints)
                    .build()

            WorkManager.getInstance()
                    .enqueueUniquePeriodicWork("loadContactsToNetwork",
                            ExistingPeriodicWorkPolicy.REPLACE, request)
        }
    }
}
