package dvachmovie.worker

import android.content.Context
import androidx.annotation.NonNull
import androidx.work.WorkerParameters
import dvachmovie.architecture.base.BaseDBWorker
import dvachmovie.di.core.WorkerComponent
import dvachmovie.repository.db.MovieDBRepository
import javax.inject.Inject

class DeleteDBWorker(@NonNull context: Context,
                     @NonNull workerParams: WorkerParameters
) : BaseDBWorker(context, workerParams) {

    @Inject
    lateinit var movieDBRepository: MovieDBRepository

    override fun inject(component: WorkerComponent) = component.inject(this)

    override fun execute() {
        movieDBRepository.deleteAll()
    }
}
