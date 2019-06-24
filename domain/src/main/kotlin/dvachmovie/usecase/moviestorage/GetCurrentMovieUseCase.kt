package dvachmovie.usecase.moviestorage

import dvachmovie.db.data.Movie
import dvachmovie.storage.MovieStorage
import dvachmovie.usecase.base.UseCase
import javax.inject.Inject

open class GetCurrentMovieUseCase @Inject constructor(
        private val movieStorage: MovieStorage) : UseCase<Unit, Movie>() {

    override fun execute(input: Unit): Movie = movieStorage.currentMovie
}
