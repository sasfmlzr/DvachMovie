package dvachmovie.repository

import dvachmovie.db.data.Thread

interface ThreadDBRepository {
    suspend fun getThreadsByNumThread(boardThread: String): List<Thread>
    suspend fun insert(threadEntity: Thread)
    suspend fun insertAll(threadEntity: List<Thread>)
    suspend fun deleteAll()
}
