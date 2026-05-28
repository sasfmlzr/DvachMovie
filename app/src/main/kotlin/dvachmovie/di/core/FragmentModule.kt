package dvachmovie.di.core

import dagger.Module
import dagger.Provides
import dvachmovie.PresenterModel
import dvachmovie.di.base.FragmentScope
import kotlinx.coroutines.flow.MutableSharedFlow

@Module
class FragmentModule {
    @Provides
    @FragmentScope
    internal fun broadcastChannel() = MutableSharedFlow<PresenterModel>(1)
}
