package dvachmovie.di.core

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dvachmovie.db.MovieDatabase
import dvachmovie.db.data.MovieDao
import dvachmovie.repository.db.LocalMovieDBRepository
import dvachmovie.repository.db.MovieDBRepository
import javax.inject.Singleton

@Module
class RoomModule(private val application: Application) {

    @Singleton
    @Provides
    internal fun providesMovieDatabase(): MovieDatabase =
            Room.databaseBuilder(application, MovieDatabase::class.java, "movieData")
            .build()

    @Singleton
    @Provides
    internal fun providesMovieDao(movieDatabase: MovieDatabase): MovieDao =
            movieDatabase.movieDao()

    @Singleton
    @Provides
    internal fun movieRepository(movieDao: MovieDao): MovieDBRepository =
            LocalMovieDBRepository(movieDao)
}
