package dvachmovie.pipe

import dvachmovie.PresenterModel
import dvachmovie.architecture.ScopeProvider
import dvachmovie.usecase.real.GetCookieUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetCookiePipe @Inject constructor(
        private val broadcastChannel: BroadcastChannel<PresenterModel>,
        private val useCase: GetCookieUseCase,
        private val scopeProvider: ScopeProvider) : Pipe<Unit>() {

    override fun execute(input: Unit) {
        scopeProvider.ioScope.launch(Job()) {
            broadcastChannel.send(CookieModel(useCase.execute(Unit)))
        }
    }
}
