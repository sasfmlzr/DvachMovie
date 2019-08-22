package dvachmovie.architecture.base

import android.content.Context
import androidx.annotation.NonNull
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dvachmovie.di.core.Injector
import dvachmovie.di.core.WorkerComponent

abstract class BaseDBWorker(@NonNull context: Context,
                            @NonNull workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    protected abstract fun inject(component: WorkerComponent)
    protected abstract suspend fun execute()

    override suspend fun doWork(): Result {
        return try {
            inject(Injector.workComponent())

            execute()
            Result.success()
        } catch (ex: RuntimeException) {
            ex.printStackTrace()
            Result.failure()
        }
    }
}
