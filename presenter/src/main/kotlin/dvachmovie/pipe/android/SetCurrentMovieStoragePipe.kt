package dvachmovie.pipe.android

import dvachmovie.architecture.PipeSync
import dvachmovie.db.data.Movie
import dvachmovie.usecase.SetCurrentMovieStorageUseCase
import javax.inject.Inject

class SetCurrentMovieStoragePipe @Inject constructor(
        private val useCase: SetCurrentMovieStorageUseCase
) : PipeSync<Movie, Unit>() {

    override fun execute(input: Movie): Unit =
            useCase.execute(input)

}
