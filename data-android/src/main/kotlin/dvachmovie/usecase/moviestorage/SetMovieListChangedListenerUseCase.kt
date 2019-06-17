package dvachmovie.usecase.moviestorage

import dvachmovie.storage.local.MovieStorage
import dvachmovie.storage.local.OnMovieListChangedListener
import dvachmovie.usecase.base.UseCase
import javax.inject.Inject

class SetMovieListChangedListenerUseCase @Inject constructor(
        private val movieStorage: MovieStorage) : UseCase<OnMovieListChangedListener, Unit>() {

    override fun execute(input: OnMovieListChangedListener) {
        movieStorage.onMovieListChangedListener = input
    }
}
