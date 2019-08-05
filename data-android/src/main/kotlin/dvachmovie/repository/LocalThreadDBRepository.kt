package dvachmovie.repository

import dvachmovie.db.data.Thread
import dvachmovie.db.model.ThreadDao
import dvachmovie.db.model.ThreadEntity
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
internal class LocalThreadDBRepository @Inject constructor(
        private val threadDao: ThreadDao) : ThreadDBRepository {

    override suspend fun getThreadsByNumThread(boardThread: String): List<Thread> {
        return threadDao.getThreadsFromThreadNum(boardThread)
    }

    override suspend fun insert(threadEntity: Thread) {
        threadDao.insert(threadEntity as ThreadEntity)
    }

    override suspend fun insertAll(threadEntity: List<Thread>) {
        threadDao.insertAll(threadEntity as List<ThreadEntity>)
    }

    override suspend fun deleteAll() {
        threadDao.deleteAll()
    }
}
