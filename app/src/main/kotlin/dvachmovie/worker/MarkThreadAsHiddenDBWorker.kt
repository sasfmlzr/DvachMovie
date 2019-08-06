package dvachmovie.worker

import android.content.Context
import androidx.annotation.NonNull
import androidx.work.WorkerParameters
import dvachmovie.architecture.base.BaseDBWorker
import dvachmovie.db.data.Movie
import dvachmovie.db.data.Thread
import dvachmovie.di.core.WorkerComponent
import dvachmovie.pipe.db.GetHiddenThreadsPipe
import dvachmovie.pipe.db.GetThreadsFromDBByNumPipe
import dvachmovie.pipe.db.InsertionThreadToDBPipe
import dvachmovie.pipe.moviestorage.GetCurrentMoviePipe
import dvachmovie.pipe.moviestorage.GetMovieListPipe
import dvachmovie.pipe.moviestorage.SetMovieListPipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MarkThreadAsHiddenDBWorker(@NonNull context: Context,
                                 @NonNull workerParams: WorkerParameters
) : BaseDBWorker(context, workerParams) {

    @Inject
    lateinit var insertionThreadToDBPipe: InsertionThreadToDBPipe

    @Inject
    lateinit var getThreadsFromDBByNumPipe: GetThreadsFromDBByNumPipe

    @Inject
    lateinit var getMovieListPipe: GetMovieListPipe

    @Inject
    lateinit var setMovieListPipe: SetMovieListPipe

    @Inject
    lateinit var getHiddenThreadsPipe: GetHiddenThreadsPipe

    @Inject
    lateinit var getCurrentMoviePipe: GetCurrentMoviePipe

    override fun inject(component: WorkerComponent) = component.inject(this)

    override suspend fun execute() {
        val numThread = inputData.getLong("NUM_THREAD", 0)

        val threads = getThreadsFromDBByNumPipe.execute(numThread)
        try {
            val currentThread: Thread = threads.first().apply {
                isHidden = true
            }
            insertionThreadToDBPipe.execute(currentThread)

            val movies = getMovieListPipe.execute(Unit)

            val hiddenThreads = getHiddenThreadsPipe.execute(Unit)
            val resultMovieList = movies.filter { movie ->
                var isThreadNotEqual = true
                hiddenThreads.forEach { thread ->
                    if (thread.thread == movie.thread) {
                        isThreadNotEqual = false
                    }
                }
                isThreadNotEqual
            }

            withContext(Dispatchers.Main) {
                setMovieListPipe.execute(resultMovieList)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    private fun findNewPosNextMovie(movies: List<Movie>) {


        val currentMovie = getCurrentMoviePipe.execute(Unit)
    }
}
