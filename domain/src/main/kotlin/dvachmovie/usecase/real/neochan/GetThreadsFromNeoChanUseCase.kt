package dvachmovie.usecase.real.neochan

import dvachmovie.architecture.logging.Logger
import dvachmovie.repository.NeoChanRepository
import dvachmovie.usecase.base.UseCase
import dvachmovie.usecase.base.UseCaseModel
import dvachmovie.usecase.real.GetThreadsFromNeoChanUseCaseModel
import javax.inject.Inject

open class GetThreadsFromNeoChanUseCase @Inject constructor(
        private val neoChanRepository: NeoChanRepository,
        private val logger: Logger) :
        UseCase<GetThreadsFromNeoChanUseCase.Params, GetThreadsFromNeoChanUseCaseModel>() {
    companion object {
        private const val TAG = "GetLinkFilesFromThreadsNeoChanUseCase"
    }

    override suspend fun executeAsync(input: Params): GetThreadsFromNeoChanUseCaseModel =
            try {
                logger.d(TAG, "connecting to neochan...")
                val numThreads = neoChanRepository.getNumThreadsFromCatalog(input.board)
                logger.d(TAG, "neochan connected")
                GetThreadsFromNeoChanUseCaseModel(numThreads)
            } catch (e: Exception) {
                logger.e(TAG, e.message ?: "Something network error")
                throw e
            }

    data class Params(val board: String) : UseCaseModel
}
