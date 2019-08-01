package dvachmovie.usecase.real.fourch

import dvachmovie.architecture.logging.Logger
import dvachmovie.repository.FourChanRepository
import dvachmovie.usecase.base.UseCase
import dvachmovie.usecase.base.UseCaseModel
import dvachmovie.usecase.real.GetThreadsFromDvachUseCaseModel
import javax.inject.Inject

open class GetThreadsFromFourchUseCase @Inject constructor(private val fourChanRepository: FourChanRepository,
                                                           private val logger: Logger) :
        UseCase<GetThreadsFromFourchUseCase.Params, GetThreadsFromDvachUseCaseModel>() {
    companion object {
        private const val TAG = "DvachUseCase"
    }

    override suspend fun executeAsync(input: Params): GetThreadsFromDvachUseCaseModel =
            try {
                logger.d(TAG, "connecting to 2.hk...")
                val numThreads = fourChanRepository.getNumThreadsFromCatalog(input.board).map { it.toString() }
                logger.d(TAG, "2.hk connected")
                GetThreadsFromDvachUseCaseModel(numThreads)
            } catch (e: Exception) {
                logger.e("GetThreadsFromDvachUseCase", e.message ?: "Something network error")
                throw e
            }

    data class Params(val board: String) : UseCaseModel
}
