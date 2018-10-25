package DB

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.IGNORE
import android.arch.persistence.room.Query
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