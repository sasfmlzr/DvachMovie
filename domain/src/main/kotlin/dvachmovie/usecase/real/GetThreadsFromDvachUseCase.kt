package dvachmovie.usecase.real

import dvachmovie.architecture.logging.Logger
import dvachmovie.repository.DvachRepository
import dvachmovie.usecase.base.ExecutorResult
import javax.inject.Inject

class GetThreadsFromDvachUseCase @Inject constructor(private val dvachRepository: DvachRepository,
                                                          private val logger: Logger) {

    companion object {
        private const val TAG = "DvachUseCase"
    }

    private lateinit var board: String
    private lateinit var executorResult: ExecutorResult

    fun addParams(board: String,
                  executorResult: ExecutorResult): GetThreadsFromDvachUseCase {
        this.board = board
        this.executorResult = executorResult
        return this
    }

    suspend fun execute() {
        logger.d(TAG, "connecting to 2.hk...")
        return try {
            val numThreads = dvachRepository.getNumThreadsFromCatalog(board)
            executorResult.onSuccess(GetThreadsFromDvachModel(numThreads))
            logger.d(TAG, "2.hk connected")
        } catch (e: Exception) {
            logger.e("GetThreadsFromDvachUseCase", e.message ?: "Something network error")
            executorResult.onFailure(e)
        }
    }
}
