package di

import DB.data.MovieDao
import DB.MovieDatabase
import DB.data.MovieEntity
import DB.repository.MovieDataSource
import DB.repository.MovieRepository
import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import android.arch.persistence.room.Room



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