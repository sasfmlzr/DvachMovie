package dvachmovie.pipe.moviestorage

import dvachmovie.architecture.PipeSync
import dvachmovie.usecase.moviestorage.MarkCurrentMovieAsPlayedUseCase
import javax.inject.Inject

class MarkCurrentMovieAsPlayedPipe @Inject constructor(
        private val useCase: MarkCurrentMovieAsPlayedUseCase
) : PipeSync<Int, Unit>() {

    override fun execute(input: Int) =
            useCase.execute(input)

}
