package dvachmovie.usecase

import dvachmovie.api.DvachMovieApi
import dvachmovie.api.model.thread.DvachThreadRequest
import dvachmovie.api.model.thread.FileItem
import dvachmovie.architecture.logging.Logger
import dvachmovie.data.BuildConfig
import dvachmovie.db.data.MovieEntity
import dvachmovie.repository.local.MovieCache
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class GetLinkFilesFromThreadsUseCase @Inject constructor(private val dvachApi: DvachMovieApi,
                                                         private val movieCache: MovieCache,
                                                         private val logger: Logger) : UseCase {

    companion object {
        private const val TAG = "GetLinkFilesFromThreadsUseCase"
    }

    private lateinit var board: String
    private lateinit var numThread: String
    private lateinit var counterWebm: CounterWebm
    private lateinit var executorResult: ExecutorResult
    private var countThread: Int = 0

    private var listMovies = mutableListOf<MovieEntity>()
    private var listFiles = mutableListOf<FileItem>()
    private var count = 0

    fun addParams(board: String,
                  numThread: String,
                  countThread: Int,
                  counterWebm: CounterWebm,
                  executorResult: ExecutorResult): GetLinkFilesFromThreadsUseCase {
        this.numThread = numThread
        this.board = board
        this.counterWebm = counterWebm
        this.executorResult = executorResult
        this.countThread = countThread
        count = 0
        listFiles = mutableListOf()
        return this
    }

    override fun execute() {
        if (!::board.isInitialized ||
                !::numThread.isInitialized ||
                !::counterWebm.isInitialized ||
                !::executorResult.isInitialized) {
            throw RuntimeException("Params must be initialized")
        }
        dvachApi.getThread(board, numThread).enqueue(dvachLinkFilesCallback)
    }

    private val dvachLinkFilesCallback = object : Callback<DvachThreadRequest> {
        override fun onResponse(call: Call<DvachThreadRequest>,
                                response: Response<DvachThreadRequest>) {
            val resp = response.body()

            val num = resp?.title
            logger.d(TAG, "parsing started for $num")

            resp?.threads?.forEach { thread ->
                thread.posts?.forEach { post ->
                    post.files?.forEach { file ->
                        listFiles.add(file)
                    }
                }
            }
            count++
            counterWebm.updateCurrentCountVideos(count)
            logger.d(TAG, "parsing finished for $num")

            if (count == countThread) {
                if (listFiles.isEmpty()) {
                    executorResult.onFailure(RuntimeException("This is a private board"))
                } else {
                    setupUriVideos(listFiles)
                }
            }
        }

        override fun onFailure(call: Call<DvachThreadRequest>, t: Throwable) {
            count++
            logger.e(TAG, "error")
            executorResult.onFailure(t)
        }
    }

    private fun setupUriVideos(fileItems: MutableList<FileItem>) {
        var count = fileItems.size
        fileItems.forEach { fileItem ->
            if (fileItem.path.contains(".webm")) {
                val movieEntity = MovieEntity(board = this.board,
                        movieUrl = BuildConfig.DVACH_URL + fileItem.path,
                        previewUrl = BuildConfig.DVACH_URL + fileItem.thumbnail)
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
