package dvachmovie.usecase

import dvachmovie.Constants
import dvachmovie.api.DvachMovieApi
import dvachmovie.api.model.catalog.DvachCatalogRequest
import dvachmovie.api.model.thread.DvachThreadRequest
import dvachmovie.api.model.thread.FileItem
import dvachmovie.architecture.logging.Logger
import dvachmovie.db.data.MovieEntity
import dvachmovie.repository.local.MovieCache
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class DvachUseCase @Inject constructor(private val dvachApi: DvachMovieApi,
                                       private val movieCache: MovieCache,
                                       private val logger: Logger) {

    companion object {
        private const val TAG = "DvachUseCase"
    }

    private var listFiles = mutableListOf<FileItem>()
    private var listMovies = mutableListOf<MovieEntity>()
    private var listMovieSize = 0
    private var count = 0

    private lateinit var board: String
    private lateinit var counterWebm: CounterWebm
    private lateinit var executorResult: ExecutorResult

    fun execute(board: String, counterWebm: CounterWebm, executorResult: ExecutorResult) {
        this.counterWebm = counterWebm
        this.board = board
        this.executorResult = executorResult
        dvachApi.getCatalog(board).enqueue(dvachNumCallback(board))
    }

    private fun dvachNumCallback(board: String): Callback<DvachCatalogRequest> {
        return object : Callback<DvachCatalogRequest> {
            override fun onResponse(call: Call<DvachCatalogRequest>,
                                    response: Response<DvachCatalogRequest>) {
                val numThreads = response.body()!!.threads?.map { it.num }

                logger.d(TAG, "connects to 2.hk...")
                numThreads?.map { num ->
                    getLinkFilesFromThreads(board, num)
                }
                logger.d(TAG, "2.hk connected")

                listMovieSize = numThreads!!.size
                counterWebm.countVideoCalculatedSumm(listMovieSize)
            }

            override fun onFailure(call: Call<DvachCatalogRequest>, t: Throwable) {
                executorResult.onFailure(t)
            }
        }
    }

    private fun getLinkFilesFromThreads(board: String, numThread: String) {
        dvachApi.getThread(board, numThread).enqueue(dvachLinkFilesCallback())
    }

    private fun dvachLinkFilesCallback(): Callback<DvachThreadRequest> {
        return object : Callback<DvachThreadRequest> {
            override fun onResponse(call: Call<DvachThreadRequest>,
                                    response: Response<DvachThreadRequest>) {
                val resp = response.body()

                val num = resp?.title
                logger.d(TAG, "parsing started for $num")
                resp?.threads?.map { thread ->
                    thread.posts?.map { post ->
                        post.files?.forEach { file ->
                            listFiles.add(file)
                        }
                    }
                }
                count++
                counterWebm.countVideoUpdates(count)
                logger.d(TAG, "parsing finished for $num")
                if (count == listMovieSize) {
                    setupUriVideos()
                }
            }

            override fun onFailure(call: Call<DvachThreadRequest>, t: Throwable) {
                count++
                executorResult.onFailure(t)
            }
        }
    }

    private fun setupUriVideos() {
        var count = listFiles.size
        listFiles.map { fileItem ->
            val path = fileItem.path
            if (path.contains(".webm")) {
                val movieEntity = MovieEntity(board = this.board,
                        movieUrl = Constants.DVACH_URL + path,
                        previewUrl = Constants.DVACH_URL + fileItem.thumbnail)
                listMovies.add(movieEntity)
            }
            count--
            if (count == 0) {
                finally()
            }
        }
    }

    private fun finally() {
        movieCache.movieList.value = listMovies
        executorResult.onSuccess()
    }
}