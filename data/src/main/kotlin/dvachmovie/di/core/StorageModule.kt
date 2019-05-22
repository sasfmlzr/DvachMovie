package dvachmovie.di.core

import dagger.Binds
import dagger.Module
import dvachmovie.api.CookieManager
import dvachmovie.api.LocalCookieManager
import dvachmovie.storage.LocalSettingsStorage
import dvachmovie.storage.SettingsStorage
import javax.inject.Singleton

@Module
internal abstract class StorageModule {

    @Binds
    @Singleton
    internal abstract fun cookieManager(localCookieManager: LocalCookieManager): CookieManager

    @Binds
    @Singleton
    internal abstract fun settingsStorage(local: LocalSettingsStorage): SettingsStorage
}
