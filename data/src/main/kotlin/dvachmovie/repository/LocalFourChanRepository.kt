package dvachmovie.repository

import dvachmovie.AppConfig
import dvachmovie.api.FileItem
import dvachmovie.api.FourchanApi
import dvachmovie.architecture.logging.Logger
import javax.inject.Inject

class LocalFourChanRepository @Inject constructor(
        private val api: FourchanApi,
        private val logger: Logger) : FourChanRepository {

    override suspend fun getNumThreadsFromCatalog(board: String): List<Pair<Int, String>> =
            api.getCatalog(board).flatMap {
                it.threads.map { thread ->
                    Pair(thread.no, thread.com)
                }
            }

    override suspend fun getConcreteThreadByNum(board: String,
                                                numThread: String,
                                                nameThread: String): List<FileItem> =
            api.getThread(board, numThread).let { request ->
                logger.d("getConcreteThreadByNum", "parsing started for $numThread")
                val list = request.posts.filter { it.tim != 0L && it.ext != ".jpg" && it.ext != ".png"}.map { post ->
                    FileItem(path = "${AppConfig.FOURCHAN_WEBM_URL}/$board/${post.tim}${post.ext}",
                            thumbnail = "${AppConfig.FOURCHAN_THUMBNAIL_URL}/$board/${post.tim}s.jpg",
                            md5 = post.md5,
                            numThread = numThread.toLong(),
                            numPost = post.no.toLong(),
                            date = post.time.toString(),
                            threadName = nameThread)
                }
                logger.d("getConcreteThreadByNum", "parsing finished for $numThread")
                list
            }
}
