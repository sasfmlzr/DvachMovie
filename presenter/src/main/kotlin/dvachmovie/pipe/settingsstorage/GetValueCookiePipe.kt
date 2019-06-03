package dvachmovie.pipe.settingsstorage

import dvachmovie.architecture.PipeSync
import dvachmovie.usecase.settingsStorage.GetValueCookieUseCase
import javax.inject.Inject

class GetValueCookiePipe @Inject constructor(
        private val useCase: GetValueCookieUseCase) : PipeSync<Unit, String>() {

    override fun execute(input: Unit): String = useCase.execute(input)

}
