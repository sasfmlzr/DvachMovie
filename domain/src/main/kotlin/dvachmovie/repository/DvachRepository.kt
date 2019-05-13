package dvachmovie.repository

import dvachmovie.api.FileItem

interface DvachRepository {
    suspend fun getNumThreadsFromCatalog(board: String) : List<String>
    suspend fun getConcreteThreadByNum(board: String, numThread: String): List<FileItem>
    suspend fun reportPost(board: String,
                           thread: Long,
                           post: Long,
                           comment: String): String?
}
