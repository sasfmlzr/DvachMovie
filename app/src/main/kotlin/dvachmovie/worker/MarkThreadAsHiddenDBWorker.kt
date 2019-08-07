package dvachmovie.worker

import android.content.Context
import androidx.annotation.NonNull
import androidx.work.WorkerParameters
import dvachmovie.AppConfig
import dvachmovie.architecture.base.BaseDBWorker
import dvachmovie.db.data.Movie
import dvachmovie.db.data.Thread
import dvachmovie.di.core.WorkerComponent
import dvachmovie.fragment.movie.PlayerCache
import dvachmovie.pipe.db.GetHiddenThreadsPipe
import dvachmovie.pipe.db.GetThreadsFromDBByNumPipe
import dvachmovie.pipe.db.InsertionThreadToDBPipe
import dvachmovie.pipe.moviestorage.GetCurrentMoviePipe
import dvachmovie.pipe.moviestorage.GetMovieListPipe
import dvachmovie.pipe.moviestorage.SetCurrentMoviePipe
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

    @Inject
    lateinit var setCurrentMoviePipe: SetCurrentMoviePipe

    override fun inject(component: WorkerComponent) = component.inject(this)

    override suspend fun execute() {
        val numThread = inputData.getLong("NUM_THREAD", 0)

        val threads = getThreadsFromDBByNumPipe.execute(Pair(numThread, AppConfig.currentBaseUrl))
        try {
            val currentThread: Thread = threads.first().apply {
                isHidden = true
            }
            insertionThreadToDBPipe.execute(currentThread)

            val movies = getMovieListPipe.execute(Unit)

            val hiddenThreads = getHiddenThreadsPipe.execute(AppConfig.currentBaseUrl)

            val result = removeMoviesByThread(movies,
                    getCurrentMoviePipe.execute(Unit),
                    hiddenThreads)

            withContext(Dispatchers.Main) {
                PlayerCache.isHideMovieByThreadTask = true
                setMovieListPipe.execute(result.second)
                setCurrentMoviePipe.execute(result.first)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    private fun removeMoviesByThread(movies: List<Movie>, currentMovie: Movie, threads: List<Thread>): Pair<Movie, List<Movie>> {
        val playedPart = movies.take(movies.indexOf(currentMovie)).filterNot { movie ->
            threads.map { it.thread }.contains(movie.thread)
        }
        val newIndex = playedPart.size

        val newPlaylist = movies.filterNot { movie ->
            threads.map { it.thread }.contains(movie.thread)
        }
        return Pair(newPlaylist[newIndex], newPlaylist)
    }
}
