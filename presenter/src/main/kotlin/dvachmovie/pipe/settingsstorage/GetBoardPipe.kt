package dvachmovie.pipe.settingsstorage

import dvachmovie.architecture.PipeSync
import dvachmovie.usecase.settingsstorage.GetBoardUseCase
import javax.inject.Inject

class GetBoardPipe @Inject constructor(
        private val useCase: GetBoardUseCase) : PipeSync<Unit, String>() {

    override fun execute(input: Unit): String = useCase.execute(input)

}
