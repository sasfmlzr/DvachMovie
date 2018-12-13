package dvachmovie.worker

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
    }
}