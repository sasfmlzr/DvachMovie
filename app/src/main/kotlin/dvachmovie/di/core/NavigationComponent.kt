package dvachmovie.di.core

import dagger.Subcomponent
import dvachmovie.fragment.back.BackFragment
import dvachmovie.activity.movie.MovieActivity
import dvachmovie.activity.start.StartActivity
import dvachmovie.di.base.ViewScope

@ViewScope
@Subcomponent()
interface NavigationComponent {
    fun inject(activity: MovieActivity)
    fun inject(activity: StartActivity)
}