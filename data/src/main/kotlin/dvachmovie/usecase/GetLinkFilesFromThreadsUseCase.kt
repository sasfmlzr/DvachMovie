package dvachmovie.usecase

import dvachmovie.api.DvachMovieApi
import dvachmovie.api.model.thread.DvachThreadRequest
import dvachmovie.api.model.thread.FileItem
import dvachmovie.architecture.logging.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class GetLinkFilesFromThreadsUseCase @Inject constructor(private val dvachApi: DvachMovieApi,
                                                         private val logger: Logger) : UseCase {

    companion object {
        private const val TAG = "GetLinkFilesFromThreadsUseCase"
    }

    private lateinit var board: String
    private lateinit var numThread: String
    private lateinit var executorResult: ExecutorResult

    fun addParams(board: String,
                  numThread: String,
                  executorResult: ExecutorResult): GetLinkFilesFromThreadsUseCase {
        this.numThread = numThread
        this.board = board
        this.executorResult = executorResult
        return this
    }

    override fun execute() {
        dvachApi.getThread(board, numThread).enqueue(dvachLinkFilesCallback)
    }

    private val dvachLinkFilesCallback = object : Callback<DvachThreadRequest> {
        override fun onResponse(call: Call<DvachThreadRequest>,
                                response: Response<DvachThreadRequest>) {
            val resp = response.body()
            val num = resp?.title
            val listFiles = mutableListOf<FileItem>()

            logger.d(TAG, "parsing started for $num")
            resp?.threads?.forEach { thread ->
                thread.posts?.forEach { post ->
                    if (post.banned == 0) {
                        listFiles.addAll(post.files?.map {
                            it.copy(thread = resp.currentThread.toLong(),
                                    num = post.num.toLong(),
                                    date = post.date)
                        } ?: listOf())
                    }
                }
            }
            logger.d(TAG, "parsing finished for $num")
            executorResult.onSuccess(GetLinkFilesFromThreadsModel(listFiles))
        }

        override fun onFailure(call: Call<DvachThreadRequest>, t: Throwable) {
            logger.e(TAG, t.message ?: "Something network error")
            executorResult.onFailure(t)
        }
    }
}
