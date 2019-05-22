package dvachmovie.di.core

import dagger.Binds
import dagger.Module
import dvachmovie.api.CookieManager
import dvachmovie.api.LocalCookieManager
import dvachmovie.storage.KeyValueStorage
import dvachmovie.storage.LocalSettingsStorage
import dvachmovie.storage.SettingsStorage
import dvachmovie.storage.base.SharedPreferencesStorage
import dvachmovie.utils.LocalMovieUtils
import dvachmovie.utils.MovieUtils
import javax.inject.Singleton

@Module
internal abstract class StorageModule {

    @Binds
    @Singleton
    internal abstract fun cookieManager(localCookieManager: LocalCookieManager): CookieManager

    @Binds
    @Singleton
    internal abstract fun keyValueStorage(local: SharedPreferencesStorage): KeyValueStorage

    @Binds
    @Singleton
    internal abstract fun settingsStorage(local: LocalSettingsStorage): SettingsStorage

    @Binds
    @Singleton
    internal abstract fun movieUtils(local: LocalMovieUtils): MovieUtils
}
