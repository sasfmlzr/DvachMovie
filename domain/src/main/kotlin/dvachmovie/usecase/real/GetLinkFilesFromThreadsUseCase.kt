package dvachmovie.usecase.real

import dvachmovie.architecture.logging.Logger
import dvachmovie.repository.DvachRepository
import dvachmovie.usecase.base.UseCase
import dvachmovie.usecase.base.UseCaseModel
import javax.inject.Inject

open class GetLinkFilesFromThreadsUseCase @Inject constructor(private val dvachRepository: DvachRepository,
                                                              private val logger: Logger) :
        UseCase<GetLinkFilesFromThreadsUseCase.Params, GetLinkFilesFromThreadsModel>() {
    companion object {
        private const val TAG = "GetLinkFilesFromThreadsUseCase"
    }

    override suspend fun execute(input: Params):
            GetLinkFilesFromThreadsModel =
            try {
                val listFiles = dvachRepository.getConcreteThreadByNum(input.board, input.numThread)
                GetLinkFilesFromThreadsModel(listFiles)
            } catch (e: RuntimeException) {
                logger.e(TAG, e.message ?: "Something network error")
                throw e
            }

    data class Params(val board: String, val numThread: String) : UseCaseModel
}
