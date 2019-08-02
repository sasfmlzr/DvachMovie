package dvachmovie.usecase.real.fourch

import dvachmovie.architecture.logging.Logger
import dvachmovie.repository.FourChanRepository
import dvachmovie.usecase.base.UseCase
import dvachmovie.usecase.base.UseCaseModel
import dvachmovie.usecase.real.GetLinkFilesFromThreadsUseCaseModel
import javax.inject.Inject

open class GetLinkFilesFromThreadsFourchUseCase @Inject constructor(private val fourChanRepository: FourChanRepository,
                                                                    private val logger: Logger) :
        UseCase<GetLinkFilesFromThreadsFourchUseCase.Params, GetLinkFilesFromThreadsUseCaseModel>() {
    companion object {
        private const val TAG = "GetLinkFilesFromThreadsFourchUseCase"
    }

    override suspend fun executeAsync(input: Params): GetLinkFilesFromThreadsUseCaseModel =
            try {
                val listFiles =
                        fourChanRepository.getConcreteThreadByNum(input.board, input.numThread)
                GetLinkFilesFromThreadsUseCaseModel(listFiles)
            } catch (e: Exception) {
                logger.e(TAG, e.message ?: "Something network error")
                throw e
            }

    data class Params(val board: String, val numThread: String) : UseCaseModel
}
