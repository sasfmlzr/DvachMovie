package dvachmovie.pipe.network

import dvachmovie.PresenterModel
import dvachmovie.architecture.PipeAsync
import dvachmovie.architecture.ScopeProvider
import dvachmovie.pipe.ErrorModel
import dvachmovie.pipe.ReportModel
import dvachmovie.usecase.base.ExecutorResult
import dvachmovie.usecase.base.UseCaseModel
import dvachmovie.usecase.real.DvachReportUseCaseModel
import dvachmovie.usecase.real.ReportUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.launch
import javax.inject.Inject

class ReportPipe @Inject constructor(
        private val broadcastChannel: BroadcastChannel<PresenterModel>,
        private val useCase: ReportUseCase,
        private val scopeProvider: ScopeProvider
) : PipeAsync<ReportUseCase.Params>() {

    override suspend fun execute(input: ReportUseCase.Params) {

        val handler = CoroutineExceptionHandler { _, throwable ->
            scopeProvider.ioScope.launch {
                if (throwable !is CancellationException) {
                    broadcastChannel.send(ErrorModel(throwable))
                }
            }
        }

        val executorResult = object : ExecutorResult {
            override fun onSuccess(useCaseModel: UseCaseModel) {
                scopeProvider.ioScope.launch(Job()) {
                    useCaseModel as DvachReportUseCaseModel
                    broadcastChannel.send(ReportModel(useCaseModel.message))
                }
            }

            override fun onFailure(t: Throwable) {
                scopeProvider.ioScope.launch(Job()) {
                    if (t !is CancellationException) {
                        broadcastChannel.send(ErrorModel(t))
                    }
                }
            }
        }

        scopeProvider.ioScope.launch(Job() + handler) {
            val inputModel = input.copy(executorResult = executorResult)
            useCase.executeAsync(inputModel)
        }
    }
}
