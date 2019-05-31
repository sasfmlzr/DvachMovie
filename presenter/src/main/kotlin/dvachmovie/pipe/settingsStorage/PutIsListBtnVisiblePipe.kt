package dvachmovie.pipe.settingsStorage

import dvachmovie.pipe.PipeAsync
import dvachmovie.usecase.settingsStorage.PutIsListBtnVisibleUseCase
import javax.inject.Inject

class PutIsListBtnVisiblePipe @Inject constructor(
        private val useCase: PutIsListBtnVisibleUseCase) : PipeAsync<Boolean>() {

    override suspend fun execute(input: Boolean) {
        useCase.executeAsync(input)
    }
}
