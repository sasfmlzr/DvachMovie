package dvachmovie.usecase

import dvachmovie.storage.local.MovieStorage
import dvachmovie.usecase.base.UseCase
import javax.inject.Inject

class MarkCurrentMovieAsPlayedUseCase @Inject constructor(
        private val movieStorage: MovieStorage
) : UseCase<Int, Unit>() {

    override suspend fun execute(input: Int) {

        movieStorage.currentMovie.value =
                movieStorage.movieList.value?.getOrNull(input).apply {
                    this?.isPlayed = true
                }
    }
}
