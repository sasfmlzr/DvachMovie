package dvachmovie.pipe.settingsstorage

import dvachmovie.architecture.PipeSync
import dvachmovie.usecase.settingsstorage.GetIsReportBtnVisibleUseCase
import javax.inject.Inject

class GetIsReportBtnVisiblePipe @Inject constructor(
        private val useCase: GetIsReportBtnVisibleUseCase) : PipeSync<Unit, Boolean>() {

    override fun execute(input: Unit): Boolean = useCase.execute(input)

}
