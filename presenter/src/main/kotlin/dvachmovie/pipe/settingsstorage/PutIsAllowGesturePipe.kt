package dvachmovie.pipe.settingsstorage

import dvachmovie.architecture.PipeAsync
import dvachmovie.usecase.settingsstorage.PutIsAllowGestureUseCase
import javax.inject.Inject

class PutIsAllowGesturePipe @Inject constructor(
        private val useCase: PutIsAllowGestureUseCase) : PipeAsync<Boolean, Unit>() {

    override suspend fun execute(input: Boolean) {
        val result = useCase.executeAsync(input)
        return result
    }
}
