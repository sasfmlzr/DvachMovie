package dvachmovie.usecase.real.dvach

import dvachmovie.architecture.logging.Logger
import dvachmovie.repository.DvachRepository
import dvachmovie.usecase.base.UseCase
import dvachmovie.usecase.base.UseCaseModel
import dvachmovie.usecase.real.GetThreadsFromDvachUseCaseModel
import javax.inject.Inject

open class GetThreadsFromDvachUseCase @Inject constructor(private val dvachRepository: DvachRepository,
                                                          private val logger: Logger) :
        UseCase<GetThreadsFromDvachUseCase.Params, GetThreadsFromDvachUseCaseModel>() {
    companion object {
        private const val TAG = "DvachUseCase"
    }

    override suspend fun executeAsync(input: Params): GetThreadsFromDvachUseCaseModel =
            try {
                logger.d(TAG, "connecting to 2.hk...")
                val numThreads = dvachRepository.getNumThreadsFromCatalog(input.board)
                logger.d(TAG, "2.hk connected")
                GetThreadsFromDvachUseCaseModel(numThreads)
            } catch (e: Exception) {
                logger.e("GetThreadsFromDvachUseCase", e.message ?: "Something network error")
                throw e
            }

    data class Params(val board: String) : UseCaseModel
}
