package dvachmovie.usecase.real.fourch

import dvachmovie.architecture.logging.Logger
import dvachmovie.repository.FourChanRepository
import dvachmovie.usecase.base.UseCase
import dvachmovie.usecase.base.UseCaseModel
import dvachmovie.usecase.real.GetThreadsFromFourchUseCaseModel
import javax.inject.Inject

open class GetThreadsFromFourchUseCase @Inject constructor(private val fourChanRepository: FourChanRepository,
                                                           private val logger: Logger) :
        UseCase<GetThreadsFromFourchUseCase.Params, GetThreadsFromFourchUseCaseModel>() {
    companion object {
        private const val TAG = "GetLinkFilesFromThreadsFourchUseCase"
    }

    override suspend fun executeAsync(input: Params): GetThreadsFromFourchUseCaseModel =
            try {
                logger.d(TAG, "connecting to 4chan...")
                val numThreads = fourChanRepository.getNumThreadsFromCatalog(input.board)
                logger.d(TAG, "4chan connected")
                GetThreadsFromFourchUseCaseModel(numThreads)
            } catch (e: Exception) {
                logger.e(TAG, e.message ?: "Something network error")
                throw e
            }

    data class Params(val board: String) : UseCaseModel
}
