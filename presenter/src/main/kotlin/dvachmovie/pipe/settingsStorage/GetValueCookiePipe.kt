package dvachmovie.pipe.settingsStorage

import dvachmovie.PresenterModel
import dvachmovie.architecture.ScopeProvider
import dvachmovie.pipe.Pipe
import dvachmovie.usecase.settingsStorage.GetValueCookieUseCase
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetValueCookiePipe @Inject constructor(
        private val broadcastChannel: BroadcastChannel<PresenterModel>,
        private val useCase: GetValueCookieUseCase,
        private val scopeProvider: ScopeProvider) : Pipe<Unit>() {

    override fun execute(input: Unit) {
        scopeProvider.ioScope.launch {
            broadcastChannel.send(CookieStringModel(useCase.execute(input)))
        }
    }
}
