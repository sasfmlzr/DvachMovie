package dvachmovie.usecase.utils

import dvachmovie.db.data.Movie
import dvachmovie.usecase.base.UseCase
import dvachmovie.utils.MovieUtils
import javax.inject.Inject

class ShuffleMoviesUseCase @Inject constructor(
        private val movieUtils: MovieUtils) : UseCase<List<Movie>, List<Movie>>() {

    override suspend fun execute(input: List<Movie>): List<Movie> {
        return movieUtils.shuffleMovies(input)
    }
}
