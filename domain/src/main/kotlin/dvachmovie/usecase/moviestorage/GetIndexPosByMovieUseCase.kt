package dvachmovie.usecase.moviestorage

import dvachmovie.db.data.Movie
import dvachmovie.storage.MovieStorage
import dvachmovie.usecase.base.UseCase
import dvachmovie.utils.MovieUtils
import javax.inject.Inject

open class GetIndexPosByMovieUseCase @Inject constructor(
        private val movieStorage: MovieStorage,
        private val movieUtils: MovieUtils) : UseCase<Movie, Int>() {

    override fun execute(input: Movie): Int = movieUtils.getIndexPosition(input,
            movieStorage.movieList)
}
