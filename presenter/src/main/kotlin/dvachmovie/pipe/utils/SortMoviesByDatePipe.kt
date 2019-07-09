package dvachmovie.pipe.utils

import dvachmovie.architecture.PipeSync
import dvachmovie.db.data.Movie
import dvachmovie.usecase.utils.SortMoviesByDateUseCase
import javax.inject.Inject

class SortMoviesByDatePipe @Inject constructor(
        private val useCase: SortMoviesByDateUseCase) : PipeSync<List<Movie>, List<Movie>>() {

    override fun execute(input: List<Movie>): List<Movie> {
        return useCase.execute(input)
    }
}
