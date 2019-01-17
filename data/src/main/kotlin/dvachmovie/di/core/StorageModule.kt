package dvachmovie.di.core

import dagger.Binds
import dagger.Module
import dvachmovie.storage.KeyValueStorage
import dvachmovie.storage.SharedPreferencesStorage
import javax.inject.Singleton

@Module
internal abstract class StorageModule {

    @Binds
    @Singleton
    internal abstract fun keyValueStorage(local: SharedPreferencesStorage): KeyValueStorage
}