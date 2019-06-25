package dvachmovie.worker

import android.content.Context
import androidx.annotation.NonNull
import androidx.work.WorkerParameters
import dvachmovie.architecture.base.BaseDBWorker
import dvachmovie.di.core.WorkerComponent
import dvachmovie.pipe.db.EraseDBPipe
import javax.inject.Inject

class DeleteDBWorker(@NonNull context: Context,
                     @NonNull workerParams: WorkerParameters
) : BaseDBWorker(context, workerParams) {

    @Inject
    lateinit var eraseDBPipe: EraseDBPipe

    override fun inject(component: WorkerComponent) = component.inject(this)

    override suspend fun execute() {
        eraseDBPipe.execute(Unit)
    }
}
