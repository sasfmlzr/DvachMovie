package dvachmovie.worker

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager

class WorkerManager {
    companion object {
        fun initDB(context: Context) {
            val request = OneTimeWorkRequestBuilder<InitDBWorker>().build()
            WorkManager.getInstance(context).enqueue(request)
        }

        fun insertMovieInDB(context: Context) {
            val request = OneTimeWorkRequestBuilder<InsertDBWorker>().build()
            WorkManager.getInstance(context).enqueue(request)
        }

        @SuppressLint("RestrictedApi")
        fun deleteAllInDB(context: Context,
                          lifecycleOwner: LifecycleOwner,
                          doOnSuccess: () -> Unit) {
            val request = OneTimeWorkRequestBuilder<DeleteDBWorker>()
                    .build()

            val mSavedWorkInfo: LiveData<WorkInfo>

            mSavedWorkInfo = WorkManager.getInstance(context).getWorkInfoByIdLiveData(request.id)

            mSavedWorkInfo.observe(
                    lifecycleOwner,
                    Observer { workStatus ->
                        if (workStatus.state == WorkInfo.State.SUCCEEDED) {
                            doOnSuccess()
                        }
                    }
            )

            WorkManager.getInstance(context).enqueue(request)
        }
    }
}
