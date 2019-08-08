package dvachmovie.repository

import dvachmovie.db.data.Thread

interface ThreadDBRepository {
    suspend fun getThreads(baseUrl: String): List<Thread>
    suspend fun getThreadByNum(boardThread: String, baseUrl: String): Thread
    suspend fun insert(threadEntity: Thread)
    suspend fun insertAll(threadEntity: List<Thread>)
    suspend fun deleteAll()
}
