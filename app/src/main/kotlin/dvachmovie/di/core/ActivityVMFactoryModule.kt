package dvachmovie.di.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dvachmovie.activity.movie.MovieActivityVM
import dvachmovie.activity.start.StartActivityVM
import dvachmovie.architecture.ViewModelFactory
import dvachmovie.architecture.ViewModelKey
import dvachmovie.di.base.ActivityScope

@Module
internal abstract class ActivityVMFactoryModule {

    @Binds
    @ActivityScope
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MovieActivityVM::class)
    internal abstract fun movieActivityVM(VM: MovieActivityVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StartActivityVM::class)
    internal abstract fun startActivityVM(VM: StartActivityVM): ViewModel
}
