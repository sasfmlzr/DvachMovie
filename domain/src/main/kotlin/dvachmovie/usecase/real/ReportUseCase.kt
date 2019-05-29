package dvachmovie.usecase.real

import dvachmovie.repository.DvachRepository
import dvachmovie.usecase.base.ExecutorResult
import dvachmovie.usecase.base.UseCase
import dvachmovie.usecase.base.UseCaseModel
import javax.inject.Inject

class ReportUseCase @Inject constructor(private val dvachRepository: DvachRepository) :
        UseCase<ReportUseCase.Params, Unit>() {

    private val comment = "Adult content"

    override suspend fun execute(input: Params) {
        try {
            val response = dvachRepository.reportPost(
                    input.board,
                    input.thread,
                    input.post,
                    comment)
            input.executorResult.onSuccess(DvachReportUseCaseModel(response ?: ""))
        } catch (e: Exception) {
            input.executorResult.onFailure(e)
        }
    }

    data class Params(val board: String,
                      val thread: Long,
                      val post: Long,
                      val executorResult: ExecutorResult) : UseCaseModel
}
