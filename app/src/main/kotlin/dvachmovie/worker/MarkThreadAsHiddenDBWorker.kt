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
import dvachmovie.pipe.db.GetThreadFromDBByNumPipe
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
    lateinit var getThreadsFromDBByNumPipe: GetThreadFromDBByNumPipe

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

        val thread = getThreadsFromDBByNumPipe.execute(Pair(numThread, AppConfig.currentBaseUrl)).apply {
            this?.isHidden = true
        }
                ?: throw RuntimeException("Current thread doesn't exist in the database. Please refresh movies.")

        insertionThreadToDBPipe.execute(thread)

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
    }

    private fun removeMoviesByThread(
            movies: List<Movie>, currentMovie: Movie, threads: List<Thread>): Pair<Movie, List<Movie>> {
        val playedPart = movies.take(movies.indexOf(currentMovie)).filterNot { movie ->
            threads.map { it.thread }.contains(movie.thread)
        }
        val newIndex = playedPart.size

        val newPlaylist = movies.filterNot { movie ->
            threads.map { it.thread }.contains(movie.thread)
        }
        return if (newPlaylist.size <= newIndex) {
            Pair(newPlaylist[0], newPlaylist)
        } else {
            Pair(newPlaylist[newIndex], newPlaylist)
        }
    }
}
