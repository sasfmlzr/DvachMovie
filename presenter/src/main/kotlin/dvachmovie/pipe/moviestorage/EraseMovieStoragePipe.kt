package dvachmovie.pipe.moviestorage

import dvachmovie.architecture.PipeSync
import dvachmovie.usecase.moviestorage.EraseMovieStorageUseCase
import javax.inject.Inject

class EraseMovieStoragePipe @Inject constructor(
        private val useCase: EraseMovieStorageUseCase
) : PipeSync<Unit, Unit>() {

    override fun execute(input: Unit): Unit =
            useCase.execute(input)

}
