package dvachmovie.di.core

import dagger.Binds
import dagger.Module
import dvachmovie.api.CookieStorage
import dvachmovie.storage.LocalCookieStorage
import dvachmovie.storage.LocalSettingsStorage
import dvachmovie.storage.MovieStorage
import dvachmovie.storage.SettingsStorage
import dvachmovie.utils.LocalMovieUtils
import dvachmovie.utils.MovieUtils
import javax.inject.Singleton

@Module
internal abstract class StorageModule {

    @Binds
    @Singleton
    internal abstract fun cookieStorage(local: LocalCookieStorage): CookieStorage

    @Binds
    @Singleton
    internal abstract fun settingsStorage(local: LocalSettingsStorage): SettingsStorage

    @Binds
    @Singleton
    internal abstract fun movieStorage(local: dvachmovie.storage.LocalMovieStorage): MovieStorage

    @Binds
    @Singleton
    internal abstract fun movieUtils(local: LocalMovieUtils): MovieUtils
}
