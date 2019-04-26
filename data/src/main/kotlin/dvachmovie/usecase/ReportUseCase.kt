package dvachmovie.usecase

import dvachmovie.api.DvachMovieApi
import dvachmovie.api.model.DvachReportRequest
import dvachmovie.architecture.logging.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ReportUseCase @Inject constructor(private val dvachApi: DvachMovieApi,
                                        private val logger: Logger) : UseCase {

    companion object {
        private const val TAG = "ReportUseCase"
    }

    private lateinit var board: String
    private var thread: Long = 0
    private lateinit var comment: String
    private var post: Long = 0
    private lateinit var executorResult: ExecutorResult

    fun addParams(board: String,
                  thread: Long,
                  post: Long,
                  executorResult: ExecutorResult): ReportUseCase {
        this.board = board
        this.thread = thread
        this.comment = "Adult content"
        this.post = post
        this.executorResult = executorResult
        return this
    }

    override fun execute() {
        dvachApi.reportPost("report",
                board,
                thread,
                comment,
                post).enqueue(dvachReportCallback)
    }

    private val dvachReportCallback = object : Callback<DvachReportRequest> {
        override fun onResponse(call: Call<DvachReportRequest>,
                                response: Response<DvachReportRequest>) {
            logger.d(TAG, "Report was successful")
            executorResult.onSuccess(DvachReportModel(response.body()?.message ?: ""))
        }

        override fun onFailure(call: Call<DvachReportRequest>, t: Throwable) {
            logger.e(TAG, t.message ?: "Something network error")
            executorResult.onFailure(t)
        }
    }
}
