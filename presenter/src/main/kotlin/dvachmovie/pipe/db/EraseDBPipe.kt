package dvachmovie.pipe.db

import dvachmovie.architecture.PipeAsync
import dvachmovie.usecase.db.EraseDBUseCase
import javax.inject.Inject

class EraseDBPipe @Inject constructor(
        private val useCase: EraseDBUseCase
) : PipeAsync<Unit, Unit>() {

    override suspend fun execute(input: Unit) {
        val result = useCase.executeAsync(input)
        return result
    }
}
