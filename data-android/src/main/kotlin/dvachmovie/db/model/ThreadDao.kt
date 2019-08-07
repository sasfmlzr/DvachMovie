package dvachmovie.db.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ThreadDao {
    @Query("SELECT * from threadData where baseUrl = :baseUrl")
    fun getThreads(baseUrl: String): List<ThreadEntity>

    @Query("SELECT * from threadData where thread = :boardThread AND baseUrl = :baseUrl")
    fun getThreadsFromThreadNum(boardThread: String, baseUrl: String): List<ThreadEntity>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun insert(thread: ThreadEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(threads: List<ThreadEntity>)

    @Query("DELETE from threadData")
    fun deleteAll()
}
