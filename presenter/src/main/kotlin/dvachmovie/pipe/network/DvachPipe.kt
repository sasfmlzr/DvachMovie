package dvachmovie.pipe.network

import dvachmovie.PresenterModel
import dvachmovie.architecture.PipeAsync
import dvachmovie.architecture.ScopeProvider
import dvachmovie.pipe.AmountRequestsModel
import dvachmovie.pipe.CountCompletedRequestsModel
import dvachmovie.pipe.DvachModel
import dvachmovie.pipe.ErrorModel
import dvachmovie.usecase.base.ExecutorResult
import dvachmovie.usecase.base.UseCaseModel
import dvachmovie.usecase.real.DvachAmountRequestsUseCaseModel
import dvachmovie.usecase.real.DvachCountRequestUseCaseModel
import dvachmovie.usecase.real.DvachUseCase
import dvachmovie.usecase.real.DvachUseCaseModel
import dvachmovie.usecase.settingsStorage.GetBoardUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DvachPipe @Inject constructor(
        private val broadcastChannel: BroadcastChannel<PresenterModel>,
        private val useCase: DvachUseCase,
        private val scopeProvider: ScopeProvider,
        private val getBoardUseCase: GetBoardUseCase) : PipeAsync<ExecutorResult?>() {

    suspend fun forceStart() {
        useCase.forceStart()
    }

    override suspend fun execute(input: ExecutorResult?) {
        val handler = CoroutineExceptionHandler { _, throwable ->
            scopeProvider.ioScope.launch {
                if (throwable !is CancellationException) {
                    broadcastChannel.send(ErrorModel(throwable))
                }
            }
        }

        val executorResult = object : ExecutorResult {
            override suspend fun onSuccess(useCaseModel: UseCaseModel) {
                when (useCaseModel) {
                    is DvachUseCaseModel ->
                        broadcastChannel.send(DvachModel(useCaseModel.movies))
                    is DvachCountRequestUseCaseModel ->
                        broadcastChannel.send(CountCompletedRequestsModel(useCaseModel.count))
                    is DvachAmountRequestsUseCaseModel ->
                        broadcastChannel.send(AmountRequestsModel(useCaseModel.max))
                }
            }

            override suspend fun onFailure(t: Throwable) {
                if (t !is CancellationException) {
                    broadcastChannel.send(ErrorModel(t))
                }
            }
        }

        withContext(handler) {
            val inputModel = if (input == null) {
                DvachUseCase.Params(getBoardUseCase.execute(Unit), executorResult)
            } else {
                DvachUseCase.Params(getBoardUseCase.execute(Unit), input)
            }

            useCase.executeAsync(inputModel)
        }
    }
}
