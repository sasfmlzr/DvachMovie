package dvachmovie.di.core

import dagger.Subcomponent
import dvachmovie.di.base.FragmentScope
import dvachmovie.fragment.back.BackFragment
import dvachmovie.fragment.movie.MovieFragment
import dvachmovie.fragment.preview.PreviewFragment
import dvachmovie.fragment.settings.SettingsFragment
import dvachmovie.fragment.start.StartFragment

@FragmentScope
@Subcomponent(modules = [FragmentModule::class, FragmentVMFactoryModule::class])
interface FragmentComponent {
    fun inject(fragment: MovieFragment)
    fun inject(fragment: PreviewFragment)
    fun inject(fragment: BackFragment)
    fun inject(fragment: SettingsFragment)
    fun inject(fragment: StartFragment)
}
