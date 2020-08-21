package dvachmovie.di

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import dvachmovie.db.MovieDatabase
import dvachmovie.db.model.MovieDao
import dvachmovie.db.model.ThreadDao
import org.joda.time.LocalDateTime
import javax.inject.Singleton

@Module
class RoomModule(private val application: Application) {

    companion object {
        private const val VERSION_DB_ONE = 1
        private const val VERSION_DB_TWO = 2
        private const val VERSION_DB_THREE = 3
        private const val VERSION_DB_FOUR = 4
        private const val VERSION_DB_FIVE = 5
        private const val VERSION_DB_SIX = 6
        private const val COUNT_REMOVED_YEARS = 1

        private val MIGRATION_1_2 = object : Migration(VERSION_DB_ONE, VERSION_DB_TWO) {
            override fun migrate(database: SupportSQLiteDatabase) {
                val date = LocalDateTime().minusYears(COUNT_REMOVED_YEARS)
                database.execSQL("ALTER TABLE movieData ADD COLUMN date TEXT DEFAULT '$date' NOT NULL")
                database.execSQL("ALTER TABLE movieData ADD COLUMN md5 TEXT DEFAULT '' NOT NULL")
            }
        }

        private val MIGRATION_1_3 = object : Migration(VERSION_DB_TWO, VERSION_DB_THREE) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE movieData ADD COLUMN thread INTEGER DEFAULT '0' NOT NULL")
                database.execSQL("ALTER TABLE movieData ADD COLUMN post INTEGER DEFAULT '0' NOT NULL")
            }
        }

        private val MIGRATION_1_4 = object : Migration(VERSION_DB_THREE, VERSION_DB_FOUR) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE movieData ADD COLUMN baseUrl TEXT DEFAULT 'https://2ch.hk' NOT NULL")
            }
        }

        private val MIGRATION_1_5 = object : Migration(VERSION_DB_FOUR, VERSION_DB_FIVE) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("" +
                        "CREATE TABLE IF NOT EXISTS threadData " +
                        "(thread INTEGER PRIMARY KEY NOT NULL, date TEXT NOT NULL, " +
                        "isHidden INTEGER NOT NULL, nameThread TEXT NOT NULL, baseUrl TEXT NOT NULL)")
            }
        }

        private val MIGRATION_1_6 = object : Migration(VERSION_DB_FIVE, VERSION_DB_SIX) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE movieData ADD COLUMN dateAddedToDB TEXT DEFAULT '' NOT NULL")
                database.execSQL("UPDATE movieData SET dateAddedToDB = date")
            }
        }
    }

    @Singleton
    @Provides
    internal fun providesMovieDatabase(): MovieDatabase =
            Room.databaseBuilder(application, MovieDatabase::class.java, "movieData")
                    .addMigrations(
                            MIGRATION_1_2, MIGRATION_1_3, MIGRATION_1_4,
                            MIGRATION_1_5, MIGRATION_1_5, MIGRATION_1_6)
                    .build()

    @Singleton
    @Provides
    internal fun providesMovieDao(movieDatabase: MovieDatabase): MovieDao =
            movieDatabase.movieDao()

    @Singleton
    @Provides
    internal fun providesThreadDao(movieDatabase: MovieDatabase): ThreadDao =
            movieDatabase.threadDao()
}
