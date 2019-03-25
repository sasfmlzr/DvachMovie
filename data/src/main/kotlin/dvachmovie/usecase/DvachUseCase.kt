package dvachmovie.usecase

import javax.inject.Inject

class DvachUseCase @Inject constructor(private val dvachUseCase: GetThreadsFromDvachUseCase,
                                       private val getLinkFilesFromThreadsUseCase: GetLinkFilesFromThreadsUseCase) : UseCase {

    private lateinit var board: String
    private lateinit var executorResult: ExecutorResult
    private lateinit var counterWebm: CounterWebm

    fun addParams(board: String,
                  counterWebm: CounterWebm,
                  executorResult: ExecutorResult): DvachUseCase {
        this.board = board
        this.counterWebm = counterWebm
        this.executorResult = executorResult
        return this
    }

    override fun execute() {
        dvachUseCase.addParams(board, counterWebm, dvachUseCaseExecutorResult).execute()
    }

    private val dvachUseCaseExecutorResult = object : ExecutorResult {
        override fun onSuccess(useCaseModel: UseCaseModel) {
            useCaseModel as GetThreadsFromDvachModel
            counterWebm.updateCountVideos(useCaseModel.listThreads.size)

            useCaseModel.listThreads.forEach { num ->
                getLinkFilesFromThreadsUseCase
                        .addParams(board, num, useCaseModel.listThreads.size, counterWebm, executorResult)
                        .execute()
            }
        }

        override fun onFailure(t: Throwable) {
            executorResult.onFailure(t)
        }
    }
}