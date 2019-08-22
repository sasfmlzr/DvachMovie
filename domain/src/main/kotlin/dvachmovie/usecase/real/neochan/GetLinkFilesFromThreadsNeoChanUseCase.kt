package dvachmovie.usecase.real.neochan

import dvachmovie.architecture.logging.Logger
import dvachmovie.repository.NeoChanRepository
import dvachmovie.usecase.base.UseCase
import dvachmovie.usecase.base.UseCaseModel
import dvachmovie.usecase.real.GetLinkFilesFromThreadsUseCaseModel
import javax.inject.Inject

open class GetLinkFilesFromThreadsNeoChanUseCase @Inject constructor(private val neoChanRepository: NeoChanRepository,
                                                                     private val logger: Logger) :
        UseCase<GetLinkFilesFromThreadsNeoChanUseCase.Params, GetLinkFilesFromThreadsUseCaseModel>() {
    companion object {
        private const val TAG = "GetLinkFilesFromThreadsFourchUseCase"
    }

    override suspend fun executeAsync(input: Params): GetLinkFilesFromThreadsUseCaseModel =
            try {
                val listFiles =
                        neoChanRepository.getConcreteThreadByNum(input.board, input.numThread, input.nameThread)
                GetLinkFilesFromThreadsUseCaseModel(listFiles)
            } catch (e: Exception) {
                logger.e(TAG, e.message ?: "Something network error")
                throw e
            }

    data class Params(val board: String, val numThread: String, val nameThread: String) : UseCaseModel
}
