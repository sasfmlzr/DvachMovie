package dvachmovie.pipe.settingsStorage

import dvachmovie.PresenterModel
import dvachmovie.architecture.ScopeProvider
import dvachmovie.pipe.Pipe
import dvachmovie.usecase.settingsStorage.GetIsReportBtnVisibleUseCase
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetIsReportBtnVisiblePipe @Inject constructor(
     //   private val broadcastChannel: BroadcastChannel<PresenterModel>,
        private val useCase: GetIsReportBtnVisibleUseCase,
        private val scopeProvider: ScopeProvider) : Pipe<Unit>() {

    override fun execute(input: Unit) {
        scopeProvider.ioScope.launch {
    //        broadcastChannel.send(IsReportBtnVisibleModel(useCase.execute(input)))
        }
    }
}
