package dvachmovie.worker

import android.annotation.SuppressLint
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager


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
    }
}