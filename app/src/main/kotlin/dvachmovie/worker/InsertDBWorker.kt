package dvachmovie.worker

import android.content.Context
import androidx.annotation.NonNull
import androidx.work.WorkerParameters
import dvachmovie.architecture.base.BaseDBWorker
import dvachmovie.db.model.MovieEntity
import dvachmovie.di.core.WorkerComponent
import dvachmovie.repository.MovieDBRepository
import dvachmovie.storage.local.MovieStorage
import javax.inject.Inject

class InsertDBWorker(@NonNull context: Context,
                     @NonNull workerParams: WorkerParameters
) : BaseDBWorker(context, workerParams) {

    @Inject
    lateinit var movieStorage: MovieStorage
    @Inject
    lateinit var movieDBRepository: MovieDBRepository

    override fun inject(component: WorkerComponent) = component.inject(this)

    override fun execute() {
        movieDBRepository.insert(movieStorage.currentMovie.value!! as MovieEntity)
    }
}
