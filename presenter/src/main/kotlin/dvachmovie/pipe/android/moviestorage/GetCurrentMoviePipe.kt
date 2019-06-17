package dvachmovie.pipe.android.moviestorage

import dvachmovie.architecture.PipeSync
import dvachmovie.db.data.Movie
import dvachmovie.usecase.moviestorage.GetCurrentMovieUseCase
import javax.inject.Inject

class GetCurrentMoviePipe @Inject constructor(
        private val useCase: GetCurrentMovieUseCase
) : PipeSync<Unit, Movie>() {

    override fun execute(input: Unit): Movie =
            useCase.execute(input)
}
