package dvachmovie.usecase

import dvachmovie.architecture.ScopeProvider
import dvachmovie.usecase.base.ExecutorResult
import dvachmovie.usecase.base.UseCaseModel
import dvachmovie.usecase.real.DvachUseCase
import dvachmovie.usecase.real.ErrorModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import javax.inject.Inject

class DvachPipe @Inject constructor(
        private val broadcastChannel: BroadcastChannel<UseCaseModel>,
        private val dvachUseCase: DvachUseCase,
        private val scopeProvider: ScopeProvider) : Pipe<DvachUseCase.Params>() {

    fun forceStart() {
        dvachUseCase.forceStart()
    }

    override suspend fun execute(input: DvachUseCase.Params) {
        val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
            scopeProvider.uiScope.launch {
                if (throwable !is CancellationException) {
                    broadcastChannel.send(ErrorModel(throwable))
                }
            }
        }

        val executorResult = object : ExecutorResult {
            override fun onSuccess(useCaseModel: UseCaseModel) {
                scopeProvider.uiScope.launch(Job()) {
                    broadcastChannel.send(useCaseModel)
                }
            }

            override fun onFailure(t: Throwable) {
                scopeProvider.uiScope.launch(Job())  {
                    if (t !is CancellationException) {
                        broadcastChannel.send(ErrorModel(t))
                    }
                }
            }
        }

        scopeProvider.ioScope.launch(Job() + handler) {
            dvachUseCase.execute(input.copy(executorResult = executorResult))
        }
    }
}
