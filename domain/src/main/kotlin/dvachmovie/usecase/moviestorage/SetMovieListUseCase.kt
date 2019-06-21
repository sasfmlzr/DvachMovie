package dvachmovie.usecase.moviestorage

import dvachmovie.db.data.Movie
import dvachmovie.storage.MovieStorage
import dvachmovie.usecase.base.UseCase
import javax.inject.Inject

class SetMovieListUseCase @Inject constructor(
        private val movieStorage: MovieStorage) : UseCase<List<Movie>, Unit>() {

    override fun execute(input: List<Movie>) {
        movieStorage.movieList = input
    }
}
