package dvachmovie.worker

import android.content.Context
import androidx.annotation.NonNull
import androidx.work.WorkerParameters
import dvachmovie.architecture.ScopeProvider
import dvachmovie.architecture.base.BaseDBWorker
import dvachmovie.di.core.WorkerComponent
import dvachmovie.usecase.moviestorage.GetCurrentMovieUseCase
import dvachmovie.usecase.InsertionMovieToDBUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class InsertDBWorker(@NonNull context: Context,
                     @NonNull workerParams: WorkerParameters
) : BaseDBWorker(context, workerParams) {

    @Inject
    lateinit var getCurrentMovieUseCase: GetCurrentMovieUseCase
    @Inject
    lateinit var insertionMovieToDBUseCase: InsertionMovieToDBUseCase
    @Inject
    lateinit var scopeProvider: ScopeProvider

    override fun inject(component: WorkerComponent) = component.inject(this)

    override fun execute() {
        scopeProvider.ioScope.launch {
            getCurrentMovieUseCase.execute(Unit).value?.let {
                insertionMovieToDBUseCase.execute(it)
            }
        }
    }
}
