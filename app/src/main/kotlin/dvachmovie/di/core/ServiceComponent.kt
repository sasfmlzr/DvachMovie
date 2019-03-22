package dvachmovie.di.core

import dagger.Subcomponent
import dvachmovie.di.base.ServiceScope
import dvachmovie.service.DownloadService

@ServiceScope
@Subcomponent
interface ServiceComponent {
    fun inject(downloadService: DownloadService)
}
