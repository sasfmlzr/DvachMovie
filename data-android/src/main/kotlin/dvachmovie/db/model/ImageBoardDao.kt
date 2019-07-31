package dvachmovie.db.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import dvachmovie.db.data.ImageBoard

@Dao
interface ImageBoardDao {
    @Query("SELECT * from imageBoardData where baseUrl = :currentBaseUrl")
    fun getMoviesFromBoard(currentBaseUrl: String): List<ImageBoardEntity>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun insert(currentImageBoardEntity: ImageBoardEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(imageBoardEntities: List<ImageBoardEntity>)

    @Query("DELETE from imageBoardData")
    fun deleteAll()
}
