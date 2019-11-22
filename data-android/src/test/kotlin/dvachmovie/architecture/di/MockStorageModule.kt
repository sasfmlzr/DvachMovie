package dvachmovie.architecture.di

import dagger.Binds
import dagger.Module
import dvachmovie.LocalScopeProvider
import dvachmovie.architecture.ScopeProvider
import dvachmovie.architecture.repository.MockMovieDBRepository
import dvachmovie.architecture.repository.MockThreadDBRepository
import dvachmovie.repository.MovieDBRepository
import dvachmovie.repository.ThreadDBRepository
import dvachmovie.storage.KeyValueStorage
import dvachmovie.storage.local.SharedPreferencesStorage
import dvachmovie.utils.LocalMovieConverter
import dvachmovie.utils.LocalThreadConverter
import dvachmovie.utils.MovieConverter
import dvachmovie.utils.ThreadConverter
import javax.inject.Singleton

@Module
internal abstract class MockStorageModule {

    @Binds
    @Singleton
    internal abstract fun keyValueStorage(local: SharedPreferencesStorage): KeyValueStorage

    @Binds
    @Singleton
    internal abstract fun scopeProvider(local: LocalScopeProvider): ScopeProvider

    @Binds
    @Singleton
    internal abstract fun movieRepository(local: MockMovieDBRepository): MovieDBRepository

    @Binds
    @Singleton
    internal abstract fun threadRepository(local: MockThreadDBRepository): ThreadDBRepository

    @Binds
    @Singleton
    internal abstract fun movieConverter(local: LocalMovieConverter): MovieConverter

    @Binds
    @Singleton
    internal abstract fun threadConverter(local: LocalThreadConverter): ThreadConverter
}
