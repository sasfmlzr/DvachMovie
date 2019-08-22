package dvachmovie.repository

import dvachmovie.AppConfig
import dvachmovie.api.FileItem
import dvachmovie.api.NeoChanApi
import dvachmovie.architecture.logging.Logger
import javax.inject.Inject

class LocalNeoChanRepository @Inject constructor(
        private val api: NeoChanApi,
        private val logger: Logger) : NeoChanRepository {

    override suspend fun getNumThreadsFromCatalog(board: String): List<Pair<Int, String>> {
        return api.getCatalog(board).flatMap {
            it.threads.map {
                val nameThread = if (it.sub == "") {
                    it.com
                } else it.sub
                Pair(it.no, nameThread)
            }
        }
    }

    override suspend fun getConcreteThreadByNum(board: String, numThread: String, nameThread: String): List<FileItem> {
        val listFiles = mutableListOf<FileItem>()
        val request = api.getThread(board, numThread)

        logger.d("getConcreteThreadByNum", "parsing started for $numThread")
        request.posts.filter { it.tim != "" }.forEach { post ->
            if (post.extraFiles.isNotEmpty()) {
                post.extraFiles.forEach {
                    listFiles.add(
                            FileItem(path = "${AppConfig.NEOCHAN_URL}/$board/src/${it.tim}${it.ext}",
                                    thumbnail = "${AppConfig.NEOCHAN_URL}/$board/thumb/${it.tim}.jpg",
                                    md5 = it.md5,
                                    numThread = numThread.toLong(),
                                    numPost = post.no.toLong(),
                                    date = post.time.toString(),
                                    threadName = nameThread))
                }
            }
            listFiles.add(
                    FileItem(path = "${AppConfig.NEOCHAN_URL}/$board/src/${post.tim}${post.ext}",
                            thumbnail = "${AppConfig.NEOCHAN_URL}/$board/thumb/${post.tim}.jpg",
                            md5 = post.md5,
                            numThread = numThread.toLong(),
                            numPost = post.no.toLong(),
                            date = post.time.toString(),
                            threadName = nameThread))
        }

        logger.d("getConcreteThreadByNum", "parsing finished for $numThread")
        return listFiles
    }
}
