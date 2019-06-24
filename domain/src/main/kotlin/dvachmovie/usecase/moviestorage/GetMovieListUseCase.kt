package dvachmovie.usecase.moviestorage

import dvachmovie.db.data.Movie
import dvachmovie.storage.MovieStorage
import dvachmovie.usecase.base.UseCase
import javax.inject.Inject

open class GetMovieListUseCase @Inject constructor(
        private val movieStorage: MovieStorage) : UseCase<Unit, List<Movie>>() {

    override fun execute(input: Unit): List<Movie> =
            movieStorage.movieList
}
