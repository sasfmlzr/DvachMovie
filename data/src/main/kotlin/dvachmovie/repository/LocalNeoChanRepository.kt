package dvachmovie.repository

import dvachmovie.AppConfig
import dvachmovie.api.FileItem
import dvachmovie.api.NeoChanApi
import dvachmovie.architecture.logging.Logger
import javax.inject.Inject

class LocalNeoChanRepository @Inject constructor(
        private val api: NeoChanApi,
        private val logger: Logger) : NeoChanRepository {

    //TODO: FIXIT
    override suspend fun getNumThreadsFromCatalog(board: String): List<Pair<Int, String>> {
        return api.getCatalog(board).flatMap { it.threads.map { Pair(it.no, it.com) } }
    }

    //TODO: FIXIT
    override suspend fun getConcreteThreadByNum(board: String, numThread: String, nameThread: String): List<FileItem> {
        val listFiles = mutableListOf<FileItem>()
        val request = api.getThread(board, numThread)

        logger.d("getConcreteThreadByNum", "parsing started for $numThread")
        request.posts.filter { it.tim.toLong() != 0L }.forEach { post ->
            listFiles.add(
                    FileItem(path = "${AppConfig.FOURCHAN_WEBM_URL}/$board/${post.tim}${post.ext}",
                            thumbnail = "${AppConfig.FOURCHAN_THUMBNAIL_URL}/$board/${post.tim}s.jpg",
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
