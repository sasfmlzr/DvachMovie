package dvachmovie.di.core

import dagger.Subcomponent
import dvachmovie.activity.movie.MovieActivity
import dvachmovie.activity.movie.MovieTestActivity
import dvachmovie.activity.start.StartActivity
import dvachmovie.di.base.ActivityScope

@ActivityScope
@Subcomponent (modules = [ActivityVMFactoryModule::class])
interface ActivityComponent {
    fun inject(activity: MovieActivity)
    fun inject(activity: MovieTestActivity)
    fun inject(activity: StartActivity)
}
