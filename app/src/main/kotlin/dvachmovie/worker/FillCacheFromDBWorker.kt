package dvachmovie.worker

import android.content.Context
import androidx.annotation.NonNull
import androidx.work.WorkerParameters
import dvachmovie.architecture.base.BaseDBWorker
import dvachmovie.di.core.WorkerComponent
import dvachmovie.pipe.db.GetMoviesFromDBByBoardPipe
import dvachmovie.pipe.db.MergeDBandCachePipe
import dvachmovie.pipe.moviestorage.SetMovieListPipe
import dvachmovie.pipe.utils.SortMoviesByDatePipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FillCacheFromDBWorker(@NonNull context: Context,
                            @NonNull workerParams: WorkerParameters
) : BaseDBWorker(context, workerParams) {

    @Inject
    lateinit var getMoviesFromDBByBoardPipe: GetMoviesFromDBByBoardPipe

    @Inject
    lateinit var mergeDBandCachePipe: MergeDBandCachePipe

    @Inject
    lateinit var setMovieListPipe: SetMovieListPipe

    @Inject
    lateinit var sortMoviesByDatePipe: SortMoviesByDatePipe

    override fun inject(component: WorkerComponent) = component.inject(this)

    override suspend fun execute() {
        val inputData = inputData.getString("BOARD")
                ?: throw RuntimeException("board cannot be null")

        val dbMovies = getMoviesFromDBByBoardPipe.execute(inputData)

        withContext(Dispatchers.Main) {
            val sumMovies = mergeDBandCachePipe.execute(dbMovies)
            setMovieListPipe.execute(sortMoviesByDatePipe.execute(sumMovies))
        }
    }
}
