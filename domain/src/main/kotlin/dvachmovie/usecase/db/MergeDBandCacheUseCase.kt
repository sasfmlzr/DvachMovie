package dvachmovie.usecase.db

import dvachmovie.db.data.Movie
import dvachmovie.storage.MovieStorage
import dvachmovie.usecase.base.UseCase
import dvachmovie.utils.MovieUtils
import javax.inject.Inject

class MergeDBandCacheUseCase @Inject constructor(
        private val movieStorage: MovieStorage,
        private val movieUtils: MovieUtils) : UseCase<List<Movie>, List<Movie>>() {

    override fun execute(input: List<Movie>): List<Movie> {
        val movieList = movieStorage.movieList
        val diffList = movieUtils.calculateDiff(movieList, input)

        return movieList + diffList
    }
}
