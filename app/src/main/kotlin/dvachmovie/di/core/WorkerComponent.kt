package dvachmovie.di.core

import dagger.Subcomponent
import dvachmovie.MovieDatabaseWorker
import dvachmovie.di.base.WorkerScope

@WorkerScope
@Subcomponent()
interface WorkerComponent {
    fun inject(mov: MovieDatabaseWorker)
}