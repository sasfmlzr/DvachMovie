package dvachmovie.pipe.settingsStorage

import dvachmovie.pipe.PipeSync
import dvachmovie.usecase.settingsStorage.GetIsListBtnVisibleUseCase
import javax.inject.Inject

class GetIsListBtnVisiblePipe @Inject constructor(
        private val useCase: GetIsListBtnVisibleUseCase) : PipeSync<Unit, Boolean>() {

    override fun execute(input: Unit): Boolean = useCase.execute(input)

}
