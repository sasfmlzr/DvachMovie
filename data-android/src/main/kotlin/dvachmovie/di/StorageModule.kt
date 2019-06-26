package dvachmovie.di

import dagger.Binds
import dagger.Module
import dvachmovie.LocalScopeProvider
import dvachmovie.architecture.ScopeProvider
import dvachmovie.repository.LocalMovieDBRepository
import dvachmovie.repository.MovieDBRepository
import dvachmovie.storage.KeyValueStorage
import dvachmovie.storage.local.SharedPreferencesStorage
import dvachmovie.utils.LocalMovieConverter
import dvachmovie.utils.MovieConverter
import javax.inject.Singleton

@Module
internal abstract class StorageModule {

    @Binds
    @Singleton
    internal abstract fun keyValueStorage(local: SharedPreferencesStorage): KeyValueStorage

    @Binds
    @Singleton
    internal abstract fun scopeProvider(local: LocalScopeProvider): ScopeProvider

    @Binds
    @Singleton
    internal abstract fun movieRepository(local: LocalMovieDBRepository): MovieDBRepository

    @Binds
    @Singleton
    internal abstract fun movieConverter(local: LocalMovieConverter): MovieConverter
}
