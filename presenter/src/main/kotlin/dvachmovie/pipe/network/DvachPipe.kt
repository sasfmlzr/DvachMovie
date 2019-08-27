package dvachmovie.pipe.network

import dvachmovie.AppConfig
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
import dvachmovie.usecase.real.DvachUseCaseModel
import dvachmovie.usecase.real.dvach.DvachUseCase
import dvachmovie.usecase.real.fourch.FourChanUseCase
import dvachmovie.usecase.real.neochan.NeoChanUseCase
import dvachmovie.usecase.settingsstorage.GetBoardUseCase
import dvachmovie.usecase.settingsstorage.GetCurrentBaseUrlUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DvachPipe @Inject constructor(
        private val broadcastChannel: BroadcastChannel<PresenterModel>,
        private val dvachUseCase: DvachUseCase,
        private val fourChanUseCase: FourChanUseCase,
        private val neoChanUseCase: NeoChanUseCase,
        private val scopeProvider: ScopeProvider,
        private val getBoardUseCase: GetBoardUseCase,
        private val currentBaseUrlUseCase: GetCurrentBaseUrlUseCase) : PipeAsync<ExecutorResult?, Flow<PresenterModel>>() {

    suspend fun forceStart() {
        when (currentBaseUrlUseCase.execute(Unit)) {
            AppConfig.DVACH_URL -> dvachUseCase.forceStart()
            AppConfig.FOURCHAN_URL -> fourChanUseCase.forceStart()
            AppConfig.NEOCHAN_URL -> neoChanUseCase.forceStart()
        }
    }

    override suspend fun execute(input: ExecutorResult?) = channelFlow {
        val handler = CoroutineExceptionHandler { _, throwable ->
            scopeProvider.ioScope.launch {
                if (throwable !is CancellationException) {
                    send(ErrorModel(throwable))
                }
            }
        }

        val executorResult = object : ExecutorResult {
            override suspend fun onSuccess(useCaseModel: UseCaseModel) {
                when (useCaseModel) {
                    is DvachUseCaseModel ->
                        send(DvachModel(useCaseModel.movies, useCaseModel.threads))
                    is DvachCountRequestUseCaseModel ->
                        send(CountCompletedRequestsModel(useCaseModel.count))
                    is DvachAmountRequestsUseCaseModel ->
                        send(AmountRequestsModel(useCaseModel.max))
                }
            }

            override suspend fun onFailure(t: Throwable) {
                if (t !is CancellationException) {
                    send(ErrorModel(t))
                }
            }
        }

        withContext(handler) {
            val inputModel = if (input == null) {
                DvachUseCase.Params(getBoardUseCase.execute(Unit), executorResult)
            } else {
                DvachUseCase.Params(getBoardUseCase.execute(Unit), input)
            }

            when (currentBaseUrlUseCase.execute(Unit)) {
                AppConfig.DVACH_URL -> dvachUseCase.executeAsync(inputModel)
                AppConfig.FOURCHAN_URL -> fourChanUseCase.executeAsync(inputModel)
                AppConfig.NEOCHAN_URL -> neoChanUseCase.executeAsync(inputModel)
            }
        }
    }
}
