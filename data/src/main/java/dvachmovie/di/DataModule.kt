package dvachmovie.di

import dagger.Module
import dagger.Provides
import dvachmovie.repository.db.MovieDBRepository
import dvachmovie.repository.local.MovieCache
import dvachmovie.repository.local.MovieRepository
import dvachmovie.repository.local.MovieStorage
import javax.inject.Singleton

@Module(includes = [RoomModule::class, ApiModule::class])
class DataModule {
    @Singleton
    @Provides
    internal fun movieStorage() = MovieStorage()

    @Singleton
    @Provides
    internal fun movieCache() = MovieCache()

    @Singleton
    @Provides
    internal fun movieRepository(movieStorage: MovieStorage, movieDBRepository: MovieDBRepository) =
            MovieRepository(movieStorage, movieDBRepository)
}