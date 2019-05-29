package dvachmovie.pipe.settingsStorage

import dvachmovie.architecture.ScopeProvider
import dvachmovie.pipe.Pipe
import dvachmovie.usecase.settingsStorage.PutIsListBtnVisibleUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class PutIsReportBtnVisiblePipe @Inject constructor(
        private val useCase: PutIsListBtnVisibleUseCase,
        private val scopeProvider: ScopeProvider) : Pipe<Boolean>() {

    override fun execute(input: Boolean) {
        scopeProvider.ioScope.launch {
            useCase.execute(input)
        }
    }
}
