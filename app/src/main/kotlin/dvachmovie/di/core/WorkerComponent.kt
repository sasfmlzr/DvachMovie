package dvachmovie.di.core

import dagger.Subcomponent
import dvachmovie.di.base.WorkerScope
import dvachmovie.worker.*

@WorkerScope
@Subcomponent
interface WorkerComponent {
    fun inject(worker: InitDBWorker)
    fun inject(worker: InsertDBWorker)
    fun inject(worker: DeleteDBWorker)
    fun inject(worker: LoadContactsWorker)
    fun inject(worker: LoadLocationWorker)
}
