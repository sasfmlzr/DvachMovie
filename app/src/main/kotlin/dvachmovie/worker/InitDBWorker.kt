package dvachmovie.worker

import android.content.Context
import androidx.annotation.NonNull
import androidx.work.WorkerParameters
import dvachmovie.architecture.base.BaseDBWorker
import dvachmovie.db.model.MovieDBCache
import dvachmovie.di.core.WorkerComponent
import dvachmovie.pipe.db.InsertionMovieListToDBPipe
import javax.inject.Inject

class InitDBWorker(@NonNull context: Context,
                   @NonNull workerParams: WorkerParameters
) : BaseDBWorker(context, workerParams) {

    @Inject
    lateinit var insertionMovieListToDBPipe: InsertionMovieListToDBPipe

    override fun inject(component: WorkerComponent) = component.inject(this)

    override suspend fun execute() {
        insertionMovieListToDBPipe.execute(MovieDBCache.movieList)
    }
}
