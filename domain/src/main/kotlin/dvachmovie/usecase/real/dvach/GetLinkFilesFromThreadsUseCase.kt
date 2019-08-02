package dvachmovie.usecase.real.dvach

import dvachmovie.architecture.logging.Logger
import dvachmovie.repository.DvachRepository
import dvachmovie.usecase.base.UseCase
import dvachmovie.usecase.base.UseCaseModel
import dvachmovie.usecase.real.GetLinkFilesFromThreadsUseCaseModel
import javax.inject.Inject

open class GetLinkFilesFromThreadsUseCase @Inject constructor(private val dvachRepository: DvachRepository,
                                                              private val logger: Logger) :
        UseCase<GetLinkFilesFromThreadsUseCase.Params, GetLinkFilesFromThreadsUseCaseModel>() {
    companion object {
        private const val TAG = "GetLinkFilesFromThreadsUseCase"
    }

    override suspend fun executeAsync(input: Params): GetLinkFilesFromThreadsUseCaseModel =
            try {
                val listFiles =
                        dvachRepository.getConcreteThreadByNum(input.board, input.numThread)
                GetLinkFilesFromThreadsUseCaseModel(listFiles)
            } catch (e: Exception) {
                logger.e(TAG, e.message ?: "Something network error")
                throw e
            }

    data class Params(val board: String, val numThread: String) : UseCaseModel
}
