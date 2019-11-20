package dvachmovie.pipe.settingsstorage

import dvachmovie.architecture.PipeAsync
import dvachmovie.usecase.settingsstorage.PutCurrentBaseUrlUseCase
import javax.inject.Inject

class PutCurrentBaseUrlPipe @Inject constructor(
        private val useCase: PutCurrentBaseUrlUseCase) : PipeAsync<String, Unit>() {

    override suspend fun execute(input: String) {
        val result = useCase.executeAsync(input)
        return result
    }
}
