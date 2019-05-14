package dvachmovie.usecase.real

import dvachmovie.architecture.logging.Logger
import dvachmovie.repository.DvachRepository
import dvachmovie.usecase.base.UseCase
import dvachmovie.usecase.base.UseCaseModel
import javax.inject.Inject

open class GetThreadsFromDvachUseCase @Inject constructor(private val dvachRepository: DvachRepository,
                                                     private val logger: Logger) : UseCase{

    companion object {
        private const val TAG = "DvachUseCase"
    }

    private lateinit var board: String

    fun addParams(board: String): GetThreadsFromDvachUseCase {
        this.board = board
        return this
    }

    override suspend fun execute(): UseCaseModel =
            try {
                logger.d(TAG, "connecting to 2.hk...")
                val numThreads = dvachRepository.getNumThreadsFromCatalog(board)
                logger.d(TAG, "2.hk connected")
                GetThreadsFromDvachModel(numThreads)
            } catch (e: Exception) {
                logger.e("GetThreadsFromDvachUseCase", e.message ?: "Something network error")
                throw e
            }
}
