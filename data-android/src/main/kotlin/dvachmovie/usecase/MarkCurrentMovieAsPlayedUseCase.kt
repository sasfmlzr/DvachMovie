package dvachmovie.usecase

import dvachmovie.storage.local.MovieStorage
import dvachmovie.usecase.base.UseCase
import javax.inject.Inject

open class MarkCurrentMovieAsPlayedUseCase @Inject constructor(
        private val movieStorage: MovieStorage
) : UseCase<Int, Unit>() {

    override fun execute(input: Int) {
        movieStorage.currentMovie.value =
                movieStorage.movieList.getOrNull(input).apply {
                    this?.isPlayed = true
                }
    }
}
