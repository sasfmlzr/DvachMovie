package dvachmovie.usecase

import dvachmovie.storage.local.MovieStorage
import dvachmovie.usecase.base.UseCase
import javax.inject.Inject

class EraseMovieStorageUseCase @Inject constructor(
        private val movieStorage: MovieStorage) : UseCase<Unit, Unit>() {

    override suspend fun execute(input: Unit) {
        movieStorage.currentMovie.value = null
        movieStorage.movieList.value = listOf()
    }
}
