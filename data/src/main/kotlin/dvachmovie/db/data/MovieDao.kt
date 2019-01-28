package dvachmovie.db.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {
    @Query("SELECT * from movieData")
    fun getAll(): LiveData<List<MovieEntity>>

    @Query("SELECT * from movieData where board = :boardThread")
    fun getMoviesFromBoard(boardThread: String): LiveData<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(movies: List<MovieEntity>)

    @Query("DELETE from movieData")
    fun deleteAll()
}
