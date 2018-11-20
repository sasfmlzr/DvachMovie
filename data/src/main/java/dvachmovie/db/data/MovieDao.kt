package dvachmovie.db.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import io.reactivex.Flowable

@Dao
interface MovieDao {
    @Query("SELECT * from movieData")
    fun getAll(): Flowable<MovieEntity>

    //@Query("SELECT * FROM movieData WHERE movieLink = :link")
    //fun linkIsExist(link: String): Flowable<User>

    @Insert(onConflict = IGNORE)
    fun insert(movieEntity: MovieEntity)

    @Query("DELETE from movieData")
    fun deleteAll()
}