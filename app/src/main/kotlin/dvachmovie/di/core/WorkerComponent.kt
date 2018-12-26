package dvachmovie.di.core

import dagger.Subcomponent
import dvachmovie.worker.InitDBWorker
import dvachmovie.di.base.WorkerScope
import dvachmovie.worker.DeleteDBWorker
import dvachmovie.worker.InsertDBWorker

@WorkerScope
@Subcomponent()
interface WorkerComponent {
    fun inject(mov: InitDBWorker)
    fun inject(mov: InsertDBWorker)
    fun inject(mov: DeleteDBWorker)
}