package dvachmovie.di.core

import dagger.Subcomponent
import dvachmovie.di.base.WorkerScope
import dvachmovie.worker.DeleteDBWorker
import dvachmovie.worker.FillCacheFromDBWorker
import dvachmovie.worker.InitDBWorker
import dvachmovie.worker.InsertDBWorker
import dvachmovie.worker.MarkThreadAsHiddenDBWorker

@WorkerScope
@Subcomponent
interface WorkerComponent {
    fun inject(worker: InitDBWorker)
    fun inject(worker: InsertDBWorker)
    fun inject(worker: DeleteDBWorker)
    fun inject(worker: FillCacheFromDBWorker)
    fun inject(worker: MarkThreadAsHiddenDBWorker)
}
