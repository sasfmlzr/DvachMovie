package dvachmovie.usecase

import dvachmovie.db.data.NullMovie
import dvachmovie.storage.MovieStorage
import dvachmovie.usecase.base.UseCase
import javax.inject.Inject

open class MarkCurrentMovieAsPlayedUseCase @Inject constructor(
        private val movieStorage: MovieStorage
) : UseCase<Int, Unit>() {

    override fun execute(input: Int) {
        movieStorage.setCurrentMovieAndUpdate(movieStorage.movieList.getOrNull(input).apply {
            this?.isPlayed = true
        } ?: NullMovie())
    }
}
