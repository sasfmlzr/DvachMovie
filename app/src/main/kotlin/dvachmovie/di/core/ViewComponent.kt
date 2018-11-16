package dvachmovie.di.core

import dagger.Subcomponent
import dvachmovie.di.base.ViewScope
import dvachmovie.fragment.movie.MovieFragment

@ViewScope
@Subcomponent()
interface ViewComponent {
    fun inject(movieFragment: MovieFragment)
}