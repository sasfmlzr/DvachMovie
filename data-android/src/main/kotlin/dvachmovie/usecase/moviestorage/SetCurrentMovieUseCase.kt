package dvachmovie.usecase.moviestorage

import dvachmovie.db.data.Movie
import dvachmovie.storage.local.MovieStorage
import dvachmovie.usecase.base.UseCase
import javax.inject.Inject

open class SetCurrentMovieUseCase @Inject constructor(
        private val movieStorage: MovieStorage) : UseCase<Movie, Unit>() {

    override fun execute(input: Movie) {
        movieStorage.currentMovie = input
    }
}
