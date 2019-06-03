package dvachmovie.pipe.settingsStorage

import dvachmovie.architecture.PipeAsync
import dvachmovie.usecase.settingsStorage.PutIsLoadingEveryTimeUseCase
import javax.inject.Inject

class PutIsLoadingEveryTimePipe @Inject constructor(
        private val useCase: PutIsLoadingEveryTimeUseCase) : PipeAsync<Boolean>() {

    override suspend fun execute(input: Boolean) {
        useCase.executeAsync(input)
    }
}
