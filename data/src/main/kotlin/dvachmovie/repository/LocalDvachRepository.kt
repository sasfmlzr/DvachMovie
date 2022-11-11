package dvachmovie.repository

import dvachmovie.api.DvachMovieApi
import dvachmovie.api.FileItem
import dvachmovie.architecture.logging.Logger
import javax.inject.Inject

class LocalDvachRepository @Inject constructor(
        private val api: DvachMovieApi,
        private val logger: Logger) : DvachRepository {

    override suspend fun getNumThreadsFromCatalog(board: String) =
            api.getCatalog(board).threads.map { it.num }

    override suspend fun getConcreteThreadByNum(board: String, numThread: String): List<FileItem> =
            api.getThread(board, numThread).let { request ->
                logger.d("getConcreteThreadByNum", "parsing started for ${request.title}")
                val list = request.threads.flatMap { thread ->
                    thread.posts.flatMap { post ->
                        post.files?.mapNotNull {
                            if (it == null) {
                                null
                            } else {
                                FileItem(path = it.path,
                                        thumbnail = it.thumbnail,
                                        md5 = it.md5,
                                        numThread = request.currentThread.toLong(),
                                        numPost = post.num.toLong(),
                                        date = post.date,
                                        threadName = request.title)
                            }
                        } ?: listOf()
                    }
                }
                logger.d("getConcreteThreadByNum", "parsing finished for ${request.title}")
                list
            }

    override suspend fun reportPost(board: String,
                                    thread: Long,
                                    post: Long,
                                    comment: String) =
            api.reportPost("report", board, thread, comment, post).message
}
