package dvachmovie.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dvachmovie.db.data.MovieDao
import dvachmovie.db.data.MovieEntity

@Database(entities = [MovieEntity::class], version = 3, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
}
