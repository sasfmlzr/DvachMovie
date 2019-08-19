package dvachmovie.db.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface MovieDao {
    @Query("SELECT * from movieData where board = :boardThread and isPlayed = 0 and baseUrl = :baseUrl")
    fun getMoviesFromBoard(baseUrl: String, boardThread: String): List<MovieEntity>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(movies: List<MovieEntity>)

    @Query("DELETE from movieData")
    fun deleteAll()

    @Delete
    fun deleteMovies(movies: List<MovieEntity>)
}
