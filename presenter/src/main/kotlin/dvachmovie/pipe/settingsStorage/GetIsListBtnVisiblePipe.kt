package dvachmovie.pipe.settingsStorage

import dvachmovie.PresenterModel
import dvachmovie.architecture.ScopeProvider
import dvachmovie.pipe.Pipe
import dvachmovie.usecase.settingsStorage.GetIsListBtnVisibleUseCase
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetIsListBtnVisiblePipe @Inject constructor(
        private val broadcastChannel: BroadcastChannel<PresenterModel>,
        private val useCase: GetIsListBtnVisibleUseCase,
        private val scopeProvider: ScopeProvider) : Pipe<Unit>() {

    override fun execute(input: Unit) {
        scopeProvider.ioScope.launch {
            broadcastChannel.send(IsListBtnVisibleModel(useCase.execute(input)))
        }
    }
}
