package dvachmovie.usecase.utils

import dvachmovie.db.data.Movie
import dvachmovie.usecase.base.UseCase
import dvachmovie.utils.MovieUtils
import javax.inject.Inject

open class SortMovieByDateUseCase @Inject constructor(
        private val movieUtils: MovieUtils
) : UseCase<List<Movie>, List<Movie>>() {

    override fun execute(input: List<Movie>): List<Movie> =
            movieUtils.sortByDate(input)
}
