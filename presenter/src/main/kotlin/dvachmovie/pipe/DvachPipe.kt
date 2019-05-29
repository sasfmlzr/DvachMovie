package dvachmovie.pipe

import dvachmovie.PresenterModel
import dvachmovie.architecture.ScopeProvider
import dvachmovie.usecase.base.ExecutorResult
import dvachmovie.usecase.base.UseCaseModel
import dvachmovie.usecase.real.DvachUseCase
import dvachmovie.usecase.real.DvachUseCaseModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.launch
import javax.inject.Inject

class DvachPipe @Inject constructor(
        private val broadcastChannel: BroadcastChannel<PresenterModel>,
        private val useCase: DvachUseCase,
        private val scopeProvider: ScopeProvider) : Pipe<DvachUseCase.Params>() {

    fun forceStart() {
        scopeProvider.ioScope.launch {
            useCase.forceStart()
        }
    }

    override fun execute(input: DvachUseCase.Params) {
        val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
            scopeProvider.ioScope.launch {
                if (throwable !is CancellationException) {
                    broadcastChannel.send(ErrorModel(throwable))
                }
            }
        }

        val executorResult = object : ExecutorResult {
            override fun onSuccess(useCaseModel: UseCaseModel) {
                scopeProvider.ioScope.launch(Job()) {
                    useCaseModel as DvachUseCaseModel
                    broadcastChannel.send(DvachModel(useCaseModel.movies))
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
            useCase.execute(input.copy(executorResult = executorResult))
        }
    }
}
