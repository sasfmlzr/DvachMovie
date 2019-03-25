package dvachmovie.di.core

import dagger.Module
import dagger.Provides
import dvachmovie.repository.db.MovieDBRepository
import dvachmovie.repository.local.MovieDBCache
import dvachmovie.repository.local.MovieRepository
import dvachmovie.repository.local.MovieStorage
import dvachmovie.storage.SettingsStorage
import javax.inject.Singleton

@Module(includes = [RoomModule::class, ApiModule::class])
class DataModule {
    @Singleton
    @Provides
    internal fun movieStorage() = MovieStorage()

    @Singleton
    @Provides
    internal fun movieDBCache() = MovieDBCache()

    @Singleton
    @Provides
    internal fun movieRepository(movieStorage: MovieStorage,
                                 movieDBRepository: MovieDBRepository,
                                 settingsStorage: SettingsStorage) =
            MovieRepository(movieStorage, movieDBRepository, settingsStorage)
}
