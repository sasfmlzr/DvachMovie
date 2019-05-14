package dvachmovie.usecase.real

import dvachmovie.architecture.logging.Logger
import dvachmovie.repository.DvachRepository
import dvachmovie.usecase.base.UseCase
import dvachmovie.usecase.base.UseCaseModel
import javax.inject.Inject

open class GetLinkFilesFromThreadsUseCase @Inject constructor(private val dvachRepository: DvachRepository,
                                                              private val logger: Logger) : UseCase {

    companion object {
        private const val TAG = "GetLinkFilesFromThreadsUseCase"
    }

    private lateinit var board: String
    private lateinit var numThread: String

    fun addParams(board: String,
                  numThread: String): GetLinkFilesFromThreadsUseCase {
        this.numThread = numThread
        this.board = board
        return this
    }

    override suspend fun execute(): UseCaseModel =
            try {
                val listFiles = dvachRepository.getConcreteThreadByNum(board, numThread)
                GetLinkFilesFromThreadsModel(listFiles)
            } catch (e: Exception) {
                logger.e(TAG, e.message ?: "Something network error")
                throw e
            }
}
