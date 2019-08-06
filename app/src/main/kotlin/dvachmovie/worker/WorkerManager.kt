package dvachmovie.worker

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager

class WorkerManager {
    companion object {
        fun initDB(context: Context, board: String) {
            val initRequest = OneTimeWorkRequestBuilder<InitDBWorker>()
                    .build()

            val fillCacheRequest = OneTimeWorkRequestBuilder<FillCacheFromDBWorker>()
                    .setInputData(Data.Builder().putString("BOARD", board).build())
                    .build()
            WorkManager.getInstance(context)
                    .beginWith(initRequest)
                    .then(fillCacheRequest)
                    .enqueue()
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

        fun fillCacheFromDB(context: Context, board: String) {
            val fillCacheRequest = OneTimeWorkRequestBuilder<FillCacheFromDBWorker>()
                    .setInputData(Data.Builder().putString("BOARD", board).build())
                    .build()
            WorkManager.getInstance(context).enqueue(fillCacheRequest)
        }

        fun markThreadAsHiddenInDB(context: Context, numThread: Long) {
            val fillCacheRequest = OneTimeWorkRequestBuilder<MarkThreadAsHiddenDBWorker>()
                    .setInputData(Data.Builder().putLong("NUM_THREAD", numThread).build())
                    .build()
            WorkManager.getInstance(context).enqueue(fillCacheRequest)
        }
    }
}
