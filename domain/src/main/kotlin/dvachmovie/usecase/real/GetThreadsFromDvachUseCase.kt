package dvachmovie.usecase.real

import dvachmovie.architecture.logging.Logger
import dvachmovie.repository.DvachRepository
import dvachmovie.usecase.base.UseCase
import dvachmovie.usecase.base.UseCaseModel
import javax.inject.Inject

open class GetThreadsFromDvachUseCase @Inject constructor(private val dvachRepository: DvachRepository,
                                                          private val logger: Logger) :
        UseCase<GetThreadsFromDvachUseCase.Params, GetThreadsFromDvachModel>() {
    companion object {
        private const val TAG = "DvachUseCase"
    }

    override suspend fun execute(input: Params): GetThreadsFromDvachModel =
            try {
                logger.d(TAG, "connecting to 2.hk...")
                val numThreads = dvachRepository.getNumThreadsFromCatalog(input.board)
                logger.d(TAG, "2.hk connected")
                GetThreadsFromDvachModel(numThreads)
            } catch (e: RuntimeException) {
                logger.e("GetThreadsFromDvachUseCase", e.message ?: "Something network error")
                throw e
            }

    data class Params(val board: String) : UseCaseModel
}
