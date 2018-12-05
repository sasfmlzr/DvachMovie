package dvachmovie.db

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import dvachmovie.repository.db.MovieRepository
import dvachmovie.repository.local.MovieCache
import dvachmovie.repository.local.MovieTempRepository
import javax.inject.Inject

class MovieDatabaseWorker(context: Context,
                          workerParams: WorkerParameters
) : Worker(context, workerParams) {

   /* @Inject
    lateinit var movieRepository: MovieRepository
    @Inject
    lateinit var movieTempRepository: MovieTempRepository
    @Inject
    lateinit var movieCache: MovieCache*/

    override fun doWork(): Worker.Result {

        return try {
            //   movieCache
           // movieRepository.insertAll(movieTempRepository.movieList.value!!)
            Worker.Result.SUCCESS
        } catch (ex: Exception) {
            Worker.Result.FAILURE
        }
    }
}