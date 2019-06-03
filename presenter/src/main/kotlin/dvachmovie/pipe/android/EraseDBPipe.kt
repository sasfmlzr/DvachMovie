package dvachmovie.pipe.android

import dvachmovie.architecture.PipeSync
import dvachmovie.usecase.EraseDBUseCase
import javax.inject.Inject

class EraseDBPipe @Inject constructor(
        private val useCase: EraseDBUseCase
) : PipeSync<Unit, Unit>() {

    override fun execute(input: Unit): Unit =
            useCase.execute(input)

}
