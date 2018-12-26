package dvachmovie.worker

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.work.OneTimeWorkRequestBuilder
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

        fun deleteAllInDB(lifecycleOwner: LifecycleOwner, doOnSuccess: () -> Unit) {
            val request = OneTimeWorkRequestBuilder<DeleteDBWorker>().build()
            WorkManager.getInstance().enqueue(request)
            WorkManager.getInstance().getStatusById(request.id).observe(lifecycleOwner,
                    Observer { workStatus ->
                        if (workStatus != null && workStatus.state.isFinished) {
                            doOnSuccess()
                        }
                    }
            )
        }
    }
}