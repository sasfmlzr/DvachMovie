package dvachmovie.worker

import android.content.Context
import androidx.annotation.NonNull
import androidx.work.WorkerParameters
import dvachmovie.architecture.ScopeProvider
import dvachmovie.architecture.base.BaseDBWorker
import dvachmovie.di.core.WorkerComponent
import dvachmovie.storage.local.MovieDBCache
import dvachmovie.usecase.InsertionMovieListToDBUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class InitDBWorker(@NonNull context: Context,
                   @NonNull workerParams: WorkerParameters
) : BaseDBWorker(context, workerParams) {

    @Inject
    lateinit var insertionMovieListToDBUseCase: InsertionMovieListToDBUseCase
    @Inject
    lateinit var scopeProvider: ScopeProvider

    override fun inject(component: WorkerComponent) = component.inject(this)

    override fun execute() {
        scopeProvider.ioScope.launch {
            insertionMovieListToDBUseCase.execute(MovieDBCache.movieList)
        }
    }
}
