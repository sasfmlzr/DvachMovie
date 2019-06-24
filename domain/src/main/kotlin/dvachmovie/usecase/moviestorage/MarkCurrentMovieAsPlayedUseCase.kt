package dvachmovie.usecase.moviestorage

import dvachmovie.db.data.NullMovie
import dvachmovie.storage.MovieStorage
import dvachmovie.usecase.base.UseCase
import javax.inject.Inject

open class MarkCurrentMovieAsPlayedUseCase @Inject constructor(
        private val movieStorage: MovieStorage
) : UseCase<Int, Unit>() {

    override fun execute(input: Int) {
        val movie = movieStorage.movieList.getOrNull(input).apply {
            this?.isPlayed = true
        } ?: NullMovie()
        movieStorage.setCurrentMovieAndUpdate(movie)
    }
}
