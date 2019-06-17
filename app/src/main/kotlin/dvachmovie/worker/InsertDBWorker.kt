package dvachmovie.worker

import android.content.Context
import androidx.annotation.NonNull
import androidx.work.WorkerParameters
import dvachmovie.architecture.base.BaseDBWorker
import dvachmovie.di.core.WorkerComponent
import dvachmovie.pipe.android.InsertionMovieToDBPipe
import dvachmovie.pipe.android.moviestorage.GetCurrentMoviePipe
import javax.inject.Inject

class InsertDBWorker(@NonNull context: Context,
                     @NonNull workerParams: WorkerParameters
) : BaseDBWorker(context, workerParams) {

    @Inject
    lateinit var getCurrentMoviePipe: GetCurrentMoviePipe
    @Inject
    lateinit var insertionMovieToDBPipe: InsertionMovieToDBPipe

    override fun inject(component: WorkerComponent) = component.inject(this)

    override fun execute() {
        insertionMovieToDBPipe.execute(getCurrentMoviePipe.execute(Unit))
    }
}
