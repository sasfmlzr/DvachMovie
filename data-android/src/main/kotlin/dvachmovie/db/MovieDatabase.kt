package dvachmovie.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dvachmovie.db.model.MovieDao
import dvachmovie.db.model.MovieEntity
import dvachmovie.db.model.ThreadDao
import dvachmovie.db.model.ThreadEntity

@Database(entities = [MovieEntity::class, ThreadEntity::class], version = 6, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun threadDao(): ThreadDao
}
