package dvachmovie.pipe.settingsstorage

import dvachmovie.architecture.PipeAsync
import dvachmovie.usecase.settingsstorage.PutCookieUseCase
import javax.inject.Inject

class PutCookiePipe @Inject constructor(
        private val useCase: PutCookieUseCase) : PipeAsync<String>() {

    override suspend fun execute(input: String) {
        useCase.executeAsync(input)
    }
}
