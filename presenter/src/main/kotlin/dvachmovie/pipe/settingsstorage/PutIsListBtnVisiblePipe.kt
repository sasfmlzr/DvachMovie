package dvachmovie.pipe.settingsstorage

import dvachmovie.architecture.PipeAsync
import dvachmovie.usecase.settingsstorage.PutIsListBtnVisibleUseCase
import javax.inject.Inject

class PutIsListBtnVisiblePipe @Inject constructor(
        private val useCase: PutIsListBtnVisibleUseCase) : PipeAsync<Boolean, Unit>() {

    override suspend fun execute(input: Boolean) {
        useCase.executeAsync(input)
    }
}
