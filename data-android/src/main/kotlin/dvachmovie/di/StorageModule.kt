package dvachmovie.di

import dagger.Binds
import dagger.Module
import dvachmovie.LocalScopeProvider
import dvachmovie.architecture.ScopeProvider
import dvachmovie.repository.LocalMovieDBRepository
import dvachmovie.repository.MovieDBRepository
import dvachmovie.storage.KeyValueStorage
import dvachmovie.storage.local.SharedPreferencesStorage
import dvachmovie.utils.LocalMovieObserver
import dvachmovie.utils.LocalMovieUtils
import dvachmovie.utils.MovieObserver
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

    @Binds
    @Singleton
    internal abstract fun scopeProvider(local: LocalScopeProvider): ScopeProvider

    @Binds
    @Singleton
    internal abstract fun movieRepository(local: LocalMovieDBRepository): MovieDBRepository

    @Binds
    @Singleton
    internal abstract fun movieObserver(local: LocalMovieObserver): MovieObserver
}
