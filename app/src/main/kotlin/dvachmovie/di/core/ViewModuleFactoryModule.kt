package dvachmovie.di.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dvachmovie.di.ViewModelFactory
import dvachmovie.di.ViewModelKey
import dvachmovie.fragment.movie.MovieViewModel
import dvachmovie.fragment.preview.PreviewViewModel

@Module
abstract class ViewModuleFactoryModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MovieViewModel::class)
    internal abstract fun movieViewModel(viewModel: MovieViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PreviewViewModel::class)
    internal abstract fun previewViewModel(viewModel: PreviewViewModel): ViewModel

}