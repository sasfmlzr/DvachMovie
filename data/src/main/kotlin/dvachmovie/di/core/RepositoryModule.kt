package dvachmovie.di.core

import dagger.Binds
import dagger.Module
import dvachmovie.repository.DvachRepository
import dvachmovie.repository.FourChanRepository
import dvachmovie.repository.LocalDvachRepository
import dvachmovie.repository.LocalFourChanRepository
import dvachmovie.repository.LocalNeoChanRepository
import dvachmovie.repository.NeoChanRepository
import javax.inject.Singleton

@Module
internal abstract class RepositoryModule {

    @Binds
    @Singleton
    internal abstract fun dvachRepository(repository: LocalDvachRepository): DvachRepository

    @Binds
    @Singleton
    internal abstract fun forchRepository(repository: LocalFourChanRepository): FourChanRepository

    @Binds
    @Singleton
    internal abstract fun neoChanRepository(repository: LocalNeoChanRepository): NeoChanRepository
}
