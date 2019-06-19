package dvachmovie.pipe.android.moviestorage

import dvachmovie.architecture.PipeSync
import dvachmovie.db.data.Movie
import dvachmovie.usecase.moviestorage.SetMovieListUseCase
import javax.inject.Inject

class SetMovieListPipe @Inject constructor(
        private val useCase: SetMovieListUseCase
) : PipeSync<List<Movie>, Unit>() {

    override fun execute(input: List<Movie>): Unit =
            useCase.execute(input)
}
