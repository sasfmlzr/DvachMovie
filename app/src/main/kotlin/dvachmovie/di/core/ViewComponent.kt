package dvachmovie.di.core

import dagger.Subcomponent
import dvachmovie.di.base.ViewScope
import dvachmovie.fragment.back.BackFragment
import dvachmovie.fragment.movie.MovieFragment
import dvachmovie.fragment.preview.PreviewFragment

@ViewScope
@Subcomponent()
interface ViewComponent {
    fun inject(movieFragment: MovieFragment)
    fun inject(previewFragment: PreviewFragment)
    fun inject(backFragment: BackFragment)
}