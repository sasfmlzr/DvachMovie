package dvachmovie.pipe

import dvachmovie.PresenterModel
import dvachmovie.architecture.ScopeProvider
import dvachmovie.usecase.base.ExecutorResult
import dvachmovie.usecase.base.UseCaseModel
import dvachmovie.usecase.real.DvachAmountRequestsUseCaseModel
import dvachmovie.usecase.real.DvachCountRequestUseCaseModel
import dvachmovie.usecase.real.DvachUseCase
import dvachmovie.usecase.real.DvachUseCaseModel
import dvachmovie.usecase.settingsStorage.GetBoardUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.launch
import javax.inject.Inject

class DvachPipe @Inject constructor(
        private val broadcastChannel: BroadcastChannel<PresenterModel>,
        private val useCase: DvachUseCase,
        private val scopeProvider: ScopeProvider,
        private val getBoardUseCase: GetBoardUseCase) : Pipe<Unit>() {

    fun forceStart() {
        scopeProvider.ioScope.launch {
            useCase.forceStart()
        }
    }

    override fun execute(input: Unit) {
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
                    when (useCaseModel) {
                        is DvachUseCaseModel ->
                            broadcastChannel.send(DvachModel(useCaseModel.movies))
                        is DvachCountRequestUseCaseModel ->
                            broadcastChannel.send(CountCompletedRequestsModel(useCaseModel.count))
                        is DvachAmountRequestsUseCaseModel ->
                            broadcastChannel.send(AmountRequestsModel(useCaseModel.max))
                    }
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
            val inputModel = DvachUseCase.Params(getBoardUseCase.execute(Unit), executorResult)
            useCase.execute(inputModel)
        }
    }
}
