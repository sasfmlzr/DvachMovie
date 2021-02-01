package dvachmovie.usecase.real.dvach

import dvachmovie.repository.DvachRepository
import dvachmovie.usecase.base.ExecutorResult
import dvachmovie.usecase.base.UseCase
import dvachmovie.usecase.base.UseCaseModel
import dvachmovie.usecase.real.DvachReportUseCaseModel
import javax.inject.Inject

open class ReportUseCase @Inject constructor(private val dvachRepository: DvachRepository) :
        UseCase<ReportUseCase.Params, Unit>() {

    private val comment = "Adult content"

    override suspend fun executeAsync(input: Params) {
        try {
            val response = dvachRepository.reportPost(
                    input.board,
                    input.thread,
                    input.post,
                    comment)
            input.executorResult?.onSuccess(DvachReportUseCaseModel(response
                    .orEmpty()))
        } catch (e: Exception) {
            input.executorResult?.onFailure(e)
        }
    }

    data class Params(val board: String,
                      val thread: Long,
                      val post: Long,
                      val executorResult: ExecutorResult? = null) : UseCaseModel
}
