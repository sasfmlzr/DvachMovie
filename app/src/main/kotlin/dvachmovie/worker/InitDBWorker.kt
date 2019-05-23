package dvachmovie.worker

import android.content.Context
import androidx.annotation.NonNull
import androidx.work.WorkerParameters
import dvachmovie.architecture.base.BaseDBWorker
import dvachmovie.di.core.WorkerComponent
import dvachmovie.repository.MovieDBRepository
import dvachmovie.storage.local.MovieDBCache
import javax.inject.Inject

class InitDBWorker(@NonNull context: Context,
                   @NonNull workerParams: WorkerParameters
) : BaseDBWorker(context, workerParams) {

    @Inject
    lateinit var movieDBRepository: MovieDBRepository

    override fun inject(component: WorkerComponent) = component.inject(this)

    override fun execute() {
        movieDBRepository.insertAll(MovieDBCache.movieList)
    }
}
