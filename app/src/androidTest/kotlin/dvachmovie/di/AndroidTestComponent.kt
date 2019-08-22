package dvachmovie.di

import dagger.Subcomponent
import dvachmovie.di.base.AndroidTestScope
import dvachmovie.worker.DeleteDBWorkerTest
import dvachmovie.worker.FillCacheFromDBWorkerTest
import dvachmovie.worker.InsertDBWorkerTest
import dvachmovie.worker.MarkThreadAsHiddenDBWorkerTest

@AndroidTestScope
@Subcomponent
interface AndroidTestComponent {
    fun inject(androidTestComponent: FillCacheFromDBWorkerTest)
    fun inject(androidTestComponent: InsertDBWorkerTest)
    fun inject(androidTestComponent: MarkThreadAsHiddenDBWorkerTest)
    fun inject(androidTestComponent: DeleteDBWorkerTest)
}
