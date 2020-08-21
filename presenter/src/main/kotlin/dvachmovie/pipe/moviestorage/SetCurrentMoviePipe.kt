package dvachmovie.pipe.moviestorage

import dvachmovie.architecture.PipeSync
import dvachmovie.db.data.Movie
import dvachmovie.usecase.moviestorage.SetCurrentMovieUseCase
import javax.inject.Inject

class SetCurrentMoviePipe @Inject constructor(
        private val useCase: SetCurrentMovieUseCase
) : PipeSync<Movie, Unit>() {

    override fun execute(input: Movie) =
            useCase.execute(input)
}
