package dvachmovie.pipe.settingsstorage

import dvachmovie.architecture.PipeSync
import dvachmovie.usecase.settingsstorage.GetCurrentBaseUrlUseCase
import javax.inject.Inject

class GetCurrentBaseUrlPipe @Inject constructor(
        private val useCase: GetCurrentBaseUrlUseCase) : PipeSync<Unit, String>() {

    override fun execute(input: Unit) =
            useCase.execute(input)
}
