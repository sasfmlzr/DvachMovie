package dvachmovie.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dvachmovie.db.MovieDatabase
import dvachmovie.db.data.MovieDao
import dvachmovie.repository.db.MovieDataSource
import dvachmovie.repository.db.MovieRepository
import javax.inject.Singleton

@Module
class RoomModule(application: Application) {

    private var movieDatabase: MovieDatabase =
            Room.databaseBuilder(application, MovieDatabase::class.java, "movieData").build()

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