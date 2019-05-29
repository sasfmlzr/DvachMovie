package dvachmovie.pipe.settingsStorage

import dvachmovie.architecture.ScopeProvider
import dvachmovie.pipe.Pipe
import dvachmovie.usecase.settingsStorage.PutBoardUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class PutBoardPipe @Inject constructor(
        private val useCase: PutBoardUseCase,
        private val scopeProvider: ScopeProvider) : Pipe<String>() {

    override fun execute(input: String) {
        scopeProvider.ioScope.launch {
            useCase.execute(input)
        }
    }
}
