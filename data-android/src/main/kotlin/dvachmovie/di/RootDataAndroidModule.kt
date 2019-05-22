package dvachmovie.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dvachmovie.repository.db.MovieDBRepository
import dvachmovie.storage.SettingsStorage
import dvachmovie.storage.local.MovieStorage
import dvachmovie.utils.MovieObserver
import dvachmovie.utils.MovieUtils
import javax.inject.Singleton

@Module(includes = [RoomModule::class,
    StorageModule::class])
class RootDataAndroidModule {

    @Provides
    @Singleton
    internal fun sharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    internal fun movieObserver(movieStorage: MovieStorage,
                                 movieDBRepository: MovieDBRepository,
                                 settingsStorage: SettingsStorage,
                                 movieUtils: MovieUtils) =
            MovieObserver(movieStorage, movieDBRepository, settingsStorage, movieUtils)


    @Singleton
    @Provides
    internal fun movieStorage() = MovieStorage()
}