package dvachmovie.di.core

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dvachmovie.repository.db.MovieDBRepository
import dvachmovie.repository.local.MovieDBCache
import dvachmovie.repository.local.MovieRepository
import dvachmovie.repository.local.MovieStorage
import dvachmovie.storage.SettingsStorage
import javax.inject.Singleton

@Module(includes = [
    ApiModule::class,
    StorageModule::class,
    RoomModule::class
])
class RootDataModule {
    @Provides
    @Singleton
    internal fun sharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    }

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
