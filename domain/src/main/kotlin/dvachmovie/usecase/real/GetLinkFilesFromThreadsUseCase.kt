package dvachmovie.usecase.real

import dvachmovie.architecture.logging.Logger
import dvachmovie.repository.DvachRepository
import dvachmovie.usecase.base.UseCase
import dvachmovie.usecase.base.UseCaseModel
import javax.inject.Inject

open class GetLinkFilesFromThreadsUseCase @Inject constructor(private val dvachRepository: DvachRepository,
                                                              private val logger: Logger) :
        UseCase<GetLinkFilesFromThreadsUseCase.Params, GetLinkFilesFromThreadsUseCaseModel>() {
    companion object {
        private const val TAG = "GetLinkFilesFromThreadsUseCase"
    }

    override suspend fun execute(input: Params): GetLinkFilesFromThreadsUseCaseModel =
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
