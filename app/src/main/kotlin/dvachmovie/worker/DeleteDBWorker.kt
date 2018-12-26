package dvachmovie.worker

import android.content.Context
import androidx.annotation.NonNull
import androidx.work.Worker
import androidx.work.WorkerParameters
import dvachmovie.di.core.Injector
import dvachmovie.repository.db.MovieDBRepository
import javax.inject.Inject

class DeleteDBWorker(@NonNull context: Context,
                     @NonNull workerParams: WorkerParameters
) : Worker(context, workerParams) {

    @Inject
    lateinit var movieDBRepository: MovieDBRepository

    override fun doWork(): Worker.Result {

        return try {
            Injector.workComponent().inject(this)

            movieDBRepository.deleteAll()
            Worker.Result.SUCCESS
        } catch (ex: Exception) {
            Worker.Result.FAILURE
        }
    }
}