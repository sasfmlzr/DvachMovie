package dvachmovie.pipe.settingsStorage

import dvachmovie.pipe.PipeSync
import dvachmovie.usecase.settingsStorage.GetBoardUseCase
import javax.inject.Inject

class GetBoardPipe @Inject constructor(
        private val useCase: GetBoardUseCase) : PipeSync<Unit, String>() {

    override fun execute(input: Unit): String = useCase.execute(input)

}
