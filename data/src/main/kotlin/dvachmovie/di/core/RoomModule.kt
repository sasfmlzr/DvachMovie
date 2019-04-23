package dvachmovie.di.core

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import dvachmovie.db.MovieDatabase
import dvachmovie.db.data.MovieDao
import dvachmovie.repository.db.LocalMovieDBRepository
import dvachmovie.repository.db.MovieDBRepository
import org.joda.time.LocalDateTime
import javax.inject.Singleton

@Module
class RoomModule(private val application: Application) {

    @Singleton
    @Provides
    internal fun providesMovieDatabase(): MovieDatabase {
        val MIGRATION_1_2 = object : Migration(1,2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                val date = LocalDateTime().minusYears(1)

                database.execSQL("ALTER TABLE movieData ADD COLUMN date TEXT DEFAULT '$date' NOT NULL")

                database.execSQL("ALTER TABLE movieData ADD COLUMN md5 TEXT DEFAULT '' NOT NULL")
            }
        }

        return Room.databaseBuilder(application, MovieDatabase::class.java, "movieData")
                .addMigrations(MIGRATION_1_2)
                .build()
    }

    @Singleton
    @Provides
    internal fun providesMovieDao(movieDatabase: MovieDatabase): MovieDao =
            movieDatabase.movieDao()

    @Singleton
    @Provides
    internal fun movieRepository(movieDao: MovieDao): MovieDBRepository =
            LocalMovieDBRepository(movieDao)
}
