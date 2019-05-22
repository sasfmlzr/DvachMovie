package dvachmovie.di

import dagger.Binds
import dagger.Module
import dvachmovie.storage.KeyValueStorage
import dvachmovie.storage.local.SharedPreferencesStorage
import dvachmovie.utils.LocalMovieUtils
import dvachmovie.utils.MovieUtils
import javax.inject.Singleton

@Module
internal abstract class StorageModule {

    @Binds
    @Singleton
    internal abstract fun keyValueStorage(local: SharedPreferencesStorage): KeyValueStorage

    @Binds
    @Singleton
    internal abstract fun movieUtils(local: LocalMovieUtils): MovieUtils
}
