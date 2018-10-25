package DB

import DB.data.MovieDao
import DB.data.MovieEntity
import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = arrayOf(MovieEntity::class), version = 1)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

}