package dvachmovie.usecase.utils

import dvachmovie.db.data.Movie
import dvachmovie.usecase.base.UseCase
import dvachmovie.utils.MovieUtils
import javax.inject.Inject

open class ShuffleMoviesUseCase @Inject constructor(
        private val movieUtils: MovieUtils) : UseCase<List<Movie>, List<Movie>>() {

    override suspend fun executeAsync(input: List<Movie>): List<Movie> =
            movieUtils.shuffleMovies(input)
}
