package dvachmovie.usecase.moviestorage

import dvachmovie.storage.MovieStorage
import dvachmovie.storage.OnMovieListChangedListener
import dvachmovie.usecase.base.UseCase
import javax.inject.Inject

open class SetMovieListChangedListenerUseCase @Inject constructor(
        private val movieStorage: MovieStorage) : UseCase<OnMovieListChangedListener, Unit>() {

    override fun execute(input: OnMovieListChangedListener) {
        movieStorage.onMovieListChangedListener = input
    }
}
