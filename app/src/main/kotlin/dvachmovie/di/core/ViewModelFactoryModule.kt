package dvachmovie.di.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dvachmovie.activity.movie.MovieActivityViewModel
import dvachmovie.activity.start.StartActivityViewModel
import dvachmovie.di.ViewModelFactory
import dvachmovie.di.ViewModelKey
import dvachmovie.fragment.back.BackViewModel
import dvachmovie.fragment.movie.MovieViewModel
import dvachmovie.fragment.preview.PreviewViewModel

@Module
abstract class ViewModelFactoryModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MovieViewModel::class)
    internal abstract fun movieVM(viewModel: MovieViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PreviewViewModel::class)
    internal abstract fun previewVM(viewModel: PreviewViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BackViewModel::class)
    internal abstract fun backVM(viewModel: BackViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieActivityViewModel::class)
    internal abstract fun movieActivityVM(viewModel: MovieActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StartActivityViewModel::class)
    internal abstract fun startActivityVM(viewModel: StartActivityViewModel): ViewModel
}