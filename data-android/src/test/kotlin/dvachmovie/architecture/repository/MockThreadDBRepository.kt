package dvachmovie.architecture.repository

import dvachmovie.db.data.NullThread
import dvachmovie.db.data.Thread
import dvachmovie.repository.ThreadDBRepository
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
internal class MockThreadDBRepository @Inject constructor() : ThreadDBRepository {

    override suspend fun getThreads(baseUrl: String): List<Thread> {
        return listOf()
    }

    override suspend fun getThreadByNum(boardThread: String, baseUrl: String): Thread? {
        return NullThread()
    }

    override suspend fun insert(threadEntity: Thread) {
    }

    override suspend fun insertAll(threadEntity: List<Thread>) {
    }

    override suspend fun deleteAll() {
    }
}
