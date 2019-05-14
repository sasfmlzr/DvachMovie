package dvachmovie.usecase.real

import dvachmovie.repository.DvachRepository
import dvachmovie.usecase.base.ExecutorResult
import javax.inject.Inject

class ReportUseCase @Inject constructor(private val dvachRepository: DvachRepository) {

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

    suspend fun execute() {
        try {
            val response = dvachRepository.reportPost(
                    board,
                    thread,
                    post,
                    comment)
            executorResult.onSuccess(DvachReportModel(response ?: ""))
        } catch (e: Exception) {
            executorResult.onFailure(e)
        }
    }
}
