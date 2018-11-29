package dvachmovie.di.core

import dagger.Subcomponent
import dvachmovie.activity.movie.MovieActivity
import dvachmovie.activity.start.StartActivity
import dvachmovie.di.base.FragmentScope

@FragmentScope
@Subcomponent()
interface ActivityComponent {
    fun inject(activity: MovieActivity)
    fun inject(activity: StartActivity)
}