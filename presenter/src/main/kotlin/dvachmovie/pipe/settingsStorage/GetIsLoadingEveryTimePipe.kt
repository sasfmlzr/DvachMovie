package dvachmovie.pipe.settingsStorage

import dvachmovie.pipe.PipeSync
import dvachmovie.usecase.settingsStorage.GetIsLoadingEveryTimeUseCase
import javax.inject.Inject

class GetIsLoadingEveryTimePipe @Inject constructor(
        private val useCase: GetIsLoadingEveryTimeUseCase) : PipeSync<Unit, Boolean>() {

    override fun execute(input: Unit): Boolean = useCase.execute(input)

}
