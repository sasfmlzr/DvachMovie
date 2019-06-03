package dvachmovie.pipe.settingsstorage

import dvachmovie.architecture.PipeAsync
import dvachmovie.usecase.settingsStorage.PutBoardUseCase
import javax.inject.Inject

class PutBoardPipe @Inject constructor(
        private val useCase: PutBoardUseCase) : PipeAsync<String>() {

    override suspend fun execute(input: String) {
        useCase.executeAsync(input)
    }
}
