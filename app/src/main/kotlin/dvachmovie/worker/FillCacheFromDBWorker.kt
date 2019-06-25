package dvachmovie.worker

import android.content.Context
import androidx.annotation.NonNull
import androidx.work.WorkerParameters
import dvachmovie.architecture.base.BaseDBWorker
import dvachmovie.di.core.WorkerComponent
import dvachmovie.pipe.moviestorage.SetMovieListPipe
import dvachmovie.usecase.db.GetMoviesFromDBByBoardUseCase
import dvachmovie.usecase.db.MergeDBandCacheUseCase
import dvachmovie.usecase.utils.SortMovieByDateUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FillCacheFromDBWorker(@NonNull context: Context,
                            @NonNull workerParams: WorkerParameters
) : BaseDBWorker(context, workerParams) {

    @Inject
    lateinit var getMoviesFromDBByBoardUseCase: GetMoviesFromDBByBoardUseCase

    @Inject
    lateinit var mergeDBandCacheUseCase: MergeDBandCacheUseCase

    @Inject
    lateinit var setMovieListPipe: SetMovieListPipe

    @Inject
    lateinit var sortMovieByDateUseCase: SortMovieByDateUseCase

    override fun inject(component: WorkerComponent) = component.inject(this)

    override suspend fun execute() {
        val inputData = inputData.getString("BOARD")
                ?: throw RuntimeException("board cannot be null")

        val dbMovies = getMoviesFromDBByBoardUseCase.executeAsync(inputData)

        withContext(Dispatchers.Main) {
            val sumMovies = mergeDBandCacheUseCase.execute(dbMovies)
            setMovieListPipe.execute(sortMovieByDateUseCase.execute(sumMovies))
        }
    }
}
