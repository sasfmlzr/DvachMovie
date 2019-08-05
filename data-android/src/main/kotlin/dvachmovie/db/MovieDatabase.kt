package dvachmovie.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dvachmovie.db.model.MovieDao
import dvachmovie.db.model.MovieEntity

@Database(entities = [MovieEntity::class], version = 6, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
}
