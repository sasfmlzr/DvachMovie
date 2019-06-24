package dvachmovie.usecase.moviestorage

import dvachmovie.db.data.NullMovie
import dvachmovie.storage.MovieStorage
import dvachmovie.usecase.base.UseCase
import javax.inject.Inject

open class EraseMovieStorageUseCase @Inject constructor(
        private val movieStorage: MovieStorage) : UseCase<Unit, Unit>() {

    override fun execute(input: Unit) {
        movieStorage.setCurrentMovieAndUpdate(NullMovie())
        movieStorage.setMovieListAndUpdate(listOf())
    }
}
