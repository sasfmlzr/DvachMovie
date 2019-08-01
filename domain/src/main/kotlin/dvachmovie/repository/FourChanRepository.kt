package dvachmovie.repository

import dvachmovie.api.FileItem

interface FourChanRepository {
    suspend fun getNumThreadsFromCatalog(board: String) : List<Int>
    suspend fun getConcreteThreadByNum(board: String, numThread: String): List<FileItem>
}
