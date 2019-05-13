package dvachmovie.usecase.real

import dvachmovie.architecture.logging.Logger
import dvachmovie.repository.DvachRepository
import dvachmovie.usecase.base.ExecutorResult
import dvachmovie.usecase.base.UseCase
import javax.inject.Inject

class GetLinkFilesFromThreadsUseCase @Inject constructor(private val dvachRepository: DvachRepository,
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

    override suspend fun execute() {

        try {
            val listFiles = dvachRepository.getConcreteThreadByNum(board, numThread)
            executorResult.onSuccess(GetLinkFilesFromThreadsModel(listFiles))

        } catch (e: Exception) {
            logger.e(TAG, e.message ?: "Something network error")
            executorResult.onFailure(e)
        }
    }
}
