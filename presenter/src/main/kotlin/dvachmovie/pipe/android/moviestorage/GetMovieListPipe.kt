package dvachmovie.pipe.android.moviestorage

import dvachmovie.architecture.PipeSync
import dvachmovie.db.data.Movie
import dvachmovie.usecase.moviestorage.GetMovieListUseCase
import javax.inject.Inject

class GetMovieListPipe @Inject constructor(
        private val useCase: GetMovieListUseCase
) : PipeSync<Unit, List<Movie>>() {

    override fun execute(input: Unit): List<Movie> =
            useCase.execute(input)

}
