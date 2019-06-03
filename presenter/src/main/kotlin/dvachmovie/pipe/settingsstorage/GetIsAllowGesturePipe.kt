package dvachmovie.pipe.settingsstorage

import dvachmovie.architecture.PipeSync
import dvachmovie.usecase.settingsStorage.GetIsAllowGestureUseCase
import javax.inject.Inject

class GetIsAllowGesturePipe @Inject constructor(
        private val useCase: GetIsAllowGestureUseCase) : PipeSync<Unit, Boolean>() {

    override fun execute(input: Unit): Boolean = useCase.execute(input)

}
