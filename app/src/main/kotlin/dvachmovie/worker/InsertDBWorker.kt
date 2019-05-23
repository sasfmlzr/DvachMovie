package dvachmovie.worker

import android.content.Context
import androidx.annotation.NonNull
import androidx.work.WorkerParameters
import dvachmovie.architecture.ScopeProvider
import dvachmovie.architecture.base.BaseDBWorker
import dvachmovie.db.model.MovieEntity
import dvachmovie.di.core.WorkerComponent
import dvachmovie.moviestorage.GetCurrentMovieUseCase
import dvachmovie.repository.MovieDBRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class InsertDBWorker(@NonNull context: Context,
                     @NonNull workerParams: WorkerParameters
) : BaseDBWorker(context, workerParams) {

    @Inject
    lateinit var getCurrentMovieUseCase: GetCurrentMovieUseCase
    @Inject
    lateinit var movieDBRepository: MovieDBRepository
    @Inject
    lateinit var scopeProvider: ScopeProvider

    override fun inject(component: WorkerComponent) = component.inject(this)

    override fun execute() {
        scopeProvider.ioScope.launch {
            movieDBRepository.insert(getCurrentMovieUseCase.execute(Unit).value as MovieEntity)
        }
    }
}
