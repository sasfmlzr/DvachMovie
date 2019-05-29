package dvachmovie.pipe.settingsStorage

import dvachmovie.PresenterModel
import dvachmovie.architecture.ScopeProvider
import dvachmovie.pipe.Pipe
import dvachmovie.usecase.settingsStorage.GetBoardUseCase
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetBoardPipe @Inject constructor(
        private val broadcastChannel: BroadcastChannel<PresenterModel>,
        private val useCase: GetBoardUseCase,
        private val scopeProvider: ScopeProvider) : Pipe<Unit>() {

    override fun execute(input: Unit) {
        scopeProvider.ioScope.launch {
            broadcastChannel.send(BoardModel(useCase.execute(input)))
        }
    }
}
