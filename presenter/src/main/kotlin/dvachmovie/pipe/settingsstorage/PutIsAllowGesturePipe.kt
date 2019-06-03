package dvachmovie.pipe.settingsstorage

import dvachmovie.architecture.PipeAsync
import dvachmovie.usecase.settingsStorage.PutIsAllowGestureUseCase
import javax.inject.Inject

class PutIsAllowGesturePipe @Inject constructor(
        private val useCase: PutIsAllowGestureUseCase) : PipeAsync<Boolean>() {

    override suspend fun execute(input: Boolean) {
        useCase.executeAsync(input)
    }
}
