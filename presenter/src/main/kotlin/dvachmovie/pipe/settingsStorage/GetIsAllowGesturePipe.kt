package dvachmovie.pipe.settingsStorage

import dvachmovie.PresenterModel
import dvachmovie.architecture.ScopeProvider
import dvachmovie.pipe.Pipe
import dvachmovie.usecase.settingsStorage.GetIsAllowGestureUseCase
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetIsAllowGesturePipe @Inject constructor(
        private val broadcastChannel: BroadcastChannel<PresenterModel>,
        private val useCase: GetIsAllowGestureUseCase,
        private val scopeProvider: ScopeProvider) : Pipe<Unit>() {

    override fun execute(input: Unit) {
        scopeProvider.ioScope.launch {
            broadcastChannel.send(IsAllowGestureModel(useCase.execute(input)))
        }
    }
}
