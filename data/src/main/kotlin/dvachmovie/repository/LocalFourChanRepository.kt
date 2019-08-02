package dvachmovie.repository

import dvachmovie.AppConfig
import dvachmovie.api.FileItem
import dvachmovie.api.FourchanApi
import dvachmovie.architecture.logging.Logger
import javax.inject.Inject

class LocalFourChanRepository @Inject constructor(
        private val api: FourchanApi,
        private val logger: Logger) : FourChanRepository {

    override suspend fun getNumThreadsFromCatalog(board: String): List<Int> =
            api.getCatalog(board).flatMap { it.threads.map { it.no } }

    override suspend fun getConcreteThreadByNum(board: String, numThread: String): List<FileItem> {
        val listFiles = mutableListOf<FileItem>()
        val request = api.getThread(board, numThread)

        logger.d("getConcreteThreadByNum", "parsing started for $numThread")
        request.posts.filter { it.tim != 0L }.forEach { post ->
            listFiles.add(
                    FileItem(path = "${AppConfig.FOURCHAN_WEBM_URL}/$board/${post.tim}${post.ext}",
                            thumbnail = "${AppConfig.FOURCHAN_THUMBNAIL_URL}/$board/${post.tim}s.jpg",
                            md5 = post.md5,
                            numThread = numThread.toLong(),
                            numPost = post.no.toLong(),
                            date = post.time.toString()))
        }

        logger.d("getConcreteThreadByNum", "parsing finished for $numThread")
        return listFiles
    }
}
