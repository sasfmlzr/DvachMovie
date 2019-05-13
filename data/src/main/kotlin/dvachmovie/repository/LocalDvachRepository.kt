package dvachmovie.repository

import dvachmovie.api.DvachMovieApi
import dvachmovie.api.FileItem
import dvachmovie.architecture.logging.Logger
import javax.inject.Inject

class LocalDvachRepository @Inject constructor(private val dvachApi: DvachMovieApi,
                                               private val logger: Logger) : BaseRepository(logger),
        DvachRepository {

    override suspend fun getNumThreadsFromCatalog(board: String) =
            safeApiCall(call = { dvachApi.getCatalog(board).await() },
                    errorMessage = "getThreadsFromCatalog return error")?.threads?.map { it.num }
                    ?: listOf()

    override suspend fun getConcreteThreadByNum(board: String, numThread: String): List<FileItem> {
        val listFiles = mutableListOf<FileItem>()
        val request = safeApiCall(call = { dvachApi.getThread(board, numThread).await() },
                errorMessage = "getConcreteThreadByNum return error")
        logger.d("getConcreteThreadByNum", "parsing started for ${request?.title}")
        request?.threads?.forEach { thread ->
            thread.posts?.forEach { post ->
                if (post.banned == 0) {
                    listFiles.addAll(post.files?.map {
                        FileItem(path = it.path,
                                thumbnail = it.thumbnail,
                                md5 = it.md5,
                                numThread = request.currentThread.toLong(),
                                numPost = post.num.toLong(),
                                date = post.date)
                    } ?: listOf())
                }
            }
        }
        logger.d("getConcreteThreadByNum", "parsing finished for ${request?.title}")
        return listFiles
    }

    override suspend fun reportPost(board: String,
                                    thread: Long,
                                    post: Long,
                                    comment: String) =
            safeApiCall(call = {
                dvachApi.reportPost("report", board, thread, comment, post).await()
            },
                    errorMessage = "Report return error")?.message
}
