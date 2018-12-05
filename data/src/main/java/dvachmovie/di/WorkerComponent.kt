package dvachmovie.di

import dagger.Subcomponent
import dvachmovie.db.MovieDatabaseWorker

@WorkerScope
@Subcomponent
interface WorkerComponent {
    fun inject(movieDatabaseWorker: MovieDatabaseWorker)
}