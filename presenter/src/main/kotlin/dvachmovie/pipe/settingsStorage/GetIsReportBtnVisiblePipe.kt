package dvachmovie.pipe.settingsStorage

import dvachmovie.architecture.PipeSync
import dvachmovie.usecase.settingsStorage.GetIsReportBtnVisibleUseCase
import javax.inject.Inject

class GetIsReportBtnVisiblePipe @Inject constructor(
        private val useCase: GetIsReportBtnVisibleUseCase) : PipeSync<Unit, Boolean>() {

    override fun execute(input: Unit): Boolean = useCase.execute(input)

}
