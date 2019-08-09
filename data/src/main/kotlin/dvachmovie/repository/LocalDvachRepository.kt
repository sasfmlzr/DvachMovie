package dvachmovie.repository

import dvachmovie.api.DvachMovieApi
import dvachmovie.api.FileItem
import dvachmovie.architecture.logging.Logger
import javax.inject.Inject

class LocalDvachRepository @Inject constructor(
        private val dvachApi: DvachMovieApi,
        private val logger: Logger) : DvachRepository {

    override suspend fun getNumThreadsFromCatalog(board: String) =
            dvachApi.getCatalog(board).threads?.map { it.num }?:
            throw RuntimeException("getThreadsFromCatalog return error")

    override suspend fun getConcreteThreadByNum(board: String, numThread: String): List<FileItem> {
        val listFiles = mutableListOf<FileItem>()
        val request =  dvachApi.getThread(board, numThread)
             //   errorMessage = "getConcreteThreadByNum return error")
        logger.d("getConcreteThreadByNum", "parsing started for ${request.title}")
        request.threads?.forEach { thread ->
            thread.posts?.forEach { post ->
                if (post.banned == 0) {
                    listFiles.addAll(post.files?.map {
                        FileItem(path = it.path,
                                thumbnail = it.thumbnail,
                                md5 = it.md5,
                                numThread = request.currentThread.toLong(),
                                numPost = post.num.toLong(),
                                date = post.date,
                                threadName = request.title)
                    } ?: listOf())
                }
            }
        }
        logger.d("getConcreteThreadByNum", "parsing finished for ${request.title}")
        return listFiles
    }

    override suspend fun reportPost(board: String,
                                    thread: Long,
                                    post: Long,
                                    comment: String) =
                dvachApi.reportPost("report", board, thread, comment, post).message
                  //  errorMessage = "Report return error")?.message
}
