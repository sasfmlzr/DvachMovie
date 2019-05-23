package dvachmovie.di.core

import dagger.Binds
import dagger.Module
import dvachmovie.api.CookieStorage
import dvachmovie.api.LocalCookieStorage
import dvachmovie.storage.LocalSettingsStorage
import dvachmovie.storage.SettingsStorage
import javax.inject.Singleton

@Module
internal abstract class StorageModule {

    @Binds
    @Singleton
    internal abstract fun cookieStorage(local: LocalCookieStorage): CookieStorage

    @Binds
    @Singleton
    internal abstract fun settingsStorage(local: LocalSettingsStorage): SettingsStorage
}
