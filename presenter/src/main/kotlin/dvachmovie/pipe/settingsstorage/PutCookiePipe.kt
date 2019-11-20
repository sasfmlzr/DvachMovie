package dvachmovie.pipe.settingsstorage

import dvachmovie.architecture.PipeAsync
import dvachmovie.usecase.settingsstorage.PutCookieUseCase
import javax.inject.Inject

class PutCookiePipe @Inject constructor(
        private val useCase: PutCookieUseCase) : PipeAsync<String, Unit>() {

    override suspend fun execute(input: String) {
        val result = useCase.executeAsync(input)
        return result
    }
}
