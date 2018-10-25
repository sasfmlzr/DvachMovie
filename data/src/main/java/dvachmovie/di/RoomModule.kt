package dvachmovie.di

import dvachmovie.db.MovieDatabase
import dvachmovie.db.data.MovieDao
import dvachmovie.db.repository.MovieDataSource
import dvachmovie.db.repository.MovieRepository
import android.app.Application
import android.arch.persistence.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RoomModule {

    private lateinit var movieDatabase: MovieDatabase

    fun RoomModule(application: Application) {
        movieDatabase = Room.databaseBuilder(application, MovieDatabase::class.java, "movieData").build()
    }

    @Singleton
    @Provides
    fun providesMovieDatabase(): MovieDatabase {
        return movieDatabase
    }

    @Singleton
    @Provides
    fun providesMovieDao(movieDatabase: MovieDatabase): MovieDao {
        return movieDatabase.movieDao()
    }

    @Singleton
    @Provides
    fun movieRepository(movieDao: MovieDao): MovieRepository {
        return MovieDataSource(movieDao)
    }
}