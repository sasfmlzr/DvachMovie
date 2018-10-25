package dvachmovie.db

import dvachmovie.db.data.MovieDao
import dvachmovie.db.data.MovieEntity
import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = arrayOf(MovieEntity::class), version = 1)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

}