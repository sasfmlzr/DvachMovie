package dvachmovie.repository

import dvachmovie.AppConfig
import dvachmovie.api.FileItem
import dvachmovie.api.NeoChanApi
import dvachmovie.architecture.logging.Logger
import javax.inject.Inject

class LocalNeoChanRepository @Inject constructor(
        private val api: NeoChanApi,
        private val logger: Logger) : NeoChanRepository {

    override suspend fun getNumThreadsFromCatalog(board: String): List<Pair<Int, String>> =
            api.getCatalog(board).flatMap {
                it.threads.map { thread ->
                    val nameThread = if (thread.sub == "") {
                        thread.com
                    } else thread.sub
                    Pair(thread.no, nameThread)
                }
            }

    override suspend fun getConcreteThreadByNum(board: String,
                                                numThread: String,
                                                nameThread: String): List<FileItem> =
            api.getThread(board, numThread).let { request ->
                logger.d("getConcreteThreadByNum", "parsing started for $numThread")
                val list = request.posts.filter { it.tim != "" }.flatMap { post ->
                    post.extraFiles.map {
                        FileItem(path = "${AppConfig.NEOCHAN_URL}/$board/src/${it.tim}${it.ext}",
                                thumbnail = "${AppConfig.NEOCHAN_URL}/$board/thumb/${it.tim}.jpg",
                                md5 = it.md5,
                                numThread = numThread.toLong(),
                                numPost = post.no.toLong(),
                                date = post.time.toString(),
                                threadName = nameThread)
                    }
                }
                logger.d("getConcreteThreadByNum", "parsing finished for $numThread")
                list
            }
}
