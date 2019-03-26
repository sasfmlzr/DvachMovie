package dvachmovie.di.core

import dagger.Binds
import dagger.Module
import dvachmovie.api.CookieManager
import dvachmovie.api.LocalCookieManager
import dvachmovie.storage.KeyValueStorage
import dvachmovie.storage.base.SharedPreferencesStorage
import javax.inject.Singleton

@Module
internal abstract class StorageModule {

    @Binds
    @Singleton
    internal abstract fun cookieManager(localCookieManager: LocalCookieManager): CookieManager

    @Binds
    @Singleton
    internal abstract fun keyValueStorage(local: SharedPreferencesStorage): KeyValueStorage
}
