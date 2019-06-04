package dvachmovie.pipe.android

import dvachmovie.architecture.PipeSync
import dvachmovie.usecase.EraseMovieStorageUseCase
import javax.inject.Inject

class EraseMovieStoragePipe @Inject constructor(
        private val useCase: EraseMovieStorageUseCase
) : PipeSync<Unit, Unit>() {

    override fun execute(input: Unit): Unit =
            useCase.execute(input)

}
