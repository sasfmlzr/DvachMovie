package dvachmovie.usecase

import dvachmovie.db.data.Movie
import dvachmovie.storage.local.MovieStorage
import dvachmovie.usecase.base.UseCase
import javax.inject.Inject

class SetCurrentMovieStorageUseCase @Inject constructor(
        private val movieStorage: MovieStorage) : UseCase<Movie, Unit>() {

    override suspend fun execute(input: Movie) {
        movieStorage.currentMovie.value = input
    }
}
