package dvachmovie.pipe.settingsStorage

import dvachmovie.architecture.PipeAsync
import dvachmovie.usecase.settingsStorage.PutIsReportBtnVisibleUseCase
import javax.inject.Inject

class PutIsReportBtnVisiblePipe @Inject constructor(
        private val useCase: PutIsReportBtnVisibleUseCase) : PipeAsync<Boolean>() {

    override suspend fun execute(input: Boolean) {
        useCase.executeAsync(input)
    }
}
