package dvachmovie.pipe.android

import dvachmovie.architecture.PipeAsync
import dvachmovie.usecase.db.EraseDBUseCase
import javax.inject.Inject

class EraseDBPipe @Inject constructor(
        private val useCase: EraseDBUseCase
) : PipeAsync<Unit>() {

    override suspend fun execute(input: Unit): Unit =
            useCase.executeAsync(input)

}
