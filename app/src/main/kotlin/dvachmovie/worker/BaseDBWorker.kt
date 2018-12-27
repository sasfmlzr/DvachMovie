package dvachmovie.worker

import android.content.Context
import androidx.annotation.NonNull
import androidx.work.Worker
import androidx.work.WorkerParameters
import dvachmovie.di.core.Injector
import dvachmovie.di.core.WorkerComponent

abstract class BaseDBWorker(@NonNull context: Context,
                            @NonNull workerParams: WorkerParameters
) : Worker(context, workerParams) {


    protected abstract fun inject(component: WorkerComponent)
    protected abstract fun execute()

    override fun doWork(): Result {
        return try {
            inject(Injector.workComponent())

            execute()
            Result.success()

        } catch (ex: Exception) {
            Result.failure()
        }
    }
}