package dvachmovie.di.core

import dagger.Binds
import dagger.Module
import dvachmovie.repository.DvachRepository
import dvachmovie.repository.LocalDvachRepository
import javax.inject.Singleton

@Module
internal abstract class RepositoryModule {

    @Binds
    @Singleton
    internal abstract fun dvachRepository(repository: LocalDvachRepository): DvachRepository
}
