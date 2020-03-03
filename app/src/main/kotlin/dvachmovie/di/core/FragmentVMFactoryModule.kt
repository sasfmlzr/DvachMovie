package dvachmovie.di.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dvachmovie.activity.movie.MovieActivityVM
import dvachmovie.activity.start.StartActivityVM
import dvachmovie.architecture.AndroidViewModelFactory
import dvachmovie.architecture.ViewModelKey
import dvachmovie.di.base.FragmentScope
import dvachmovie.fragment.alonemovie.AloneMovieVM
import dvachmovie.fragment.back.BackVM
import dvachmovie.fragment.movie.MovieVM
import dvachmovie.fragment.preview.PreviewVM
import dvachmovie.fragment.settings.SettingsVM
import dvachmovie.fragment.start.StartVM

@Module
internal abstract class FragmentVMFactoryModule {
    @Binds
    @FragmentScope
    internal abstract fun bindViewModelAndroidFactory(factory: AndroidViewModelFactory):
            ViewModelProvider.AndroidViewModelFactory

    @Binds
    @IntoMap
    @ViewModelKey(MovieVM::class)
    internal abstract fun movieVM(VM: MovieVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AloneMovieVM::class)
    internal abstract fun aloneMovieVM(VM: AloneMovieVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PreviewVM::class)
    internal abstract fun previewVM(VM: PreviewVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BackVM::class)
    internal abstract fun backVM(VM: BackVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsVM::class)
    internal abstract fun settingsVM(VM: SettingsVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StartVM::class)
    internal abstract fun startVM(VM: StartVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieActivityVM::class)
    internal abstract fun movieActivityVM(VM: MovieActivityVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StartActivityVM::class)
    internal abstract fun startActivityVM(VM: StartActivityVM): ViewModel
}
