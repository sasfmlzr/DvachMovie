package dvachmovie

import android.content.Context
import androidx.annotation.NonNull
import androidx.work.Worker
import androidx.work.WorkerParameters
import dvachmovie.di.core.Injector
import dvachmovie.repository.db.MovieRepository
import dvachmovie.repository.local.MovieCache
import javax.inject.Inject

class MovieDatabaseWorker(@NonNull context: Context,
                          @NonNull workerParams: WorkerParameters
) : Worker(context, workerParams) {

    @Inject
    lateinit var movieCaches: MovieCache
    @Inject
    lateinit var movieRepository: MovieRepository

    override fun doWork(): Worker.Result {

        return try {
            Injector.workComponent().inject(this)

            movieRepository.insertAll(movieCaches.movieList.value!!)
            Worker.Result.SUCCESS
        } catch (ex: Exception) {
            Worker.Result.FAILURE
        }
    }
}