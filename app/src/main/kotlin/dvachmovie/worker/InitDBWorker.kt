package dvachmovie.worker

import android.content.Context
import androidx.annotation.NonNull
import androidx.work.WorkerParameters
import dvachmovie.architecture.base.BaseDBWorker
import dvachmovie.di.core.WorkerComponent
import dvachmovie.repository.db.MovieDBRepository
import dvachmovie.repository.local.MovieDBCache
import javax.inject.Inject

class InitDBWorker(@NonNull context: Context,
                   @NonNull workerParams: WorkerParameters
) : BaseDBWorker(context, workerParams) {

    @Inject
    lateinit var movieCaches: MovieDBCache
    @Inject
    lateinit var movieDBRepository: MovieDBRepository

    override fun inject(component: WorkerComponent) = component.inject(this)

    override fun execute() {
        movieDBRepository.insertAll(movieCaches.movieList.value!!)
    }

}
