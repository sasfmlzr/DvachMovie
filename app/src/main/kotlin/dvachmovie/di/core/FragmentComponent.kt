package dvachmovie.di.core

import dagger.Subcomponent
import dvachmovie.di.base.FragmentScope
import dvachmovie.fragment.back.BackFragment
import dvachmovie.fragment.movie.MovieFragment
import dvachmovie.fragment.preview.PreviewFragment

@FragmentScope
@Subcomponent()
interface FragmentComponent {
    fun inject(movieFragment: MovieFragment)
    fun inject(previewFragment: PreviewFragment)
    fun inject(backFragment: BackFragment)
}