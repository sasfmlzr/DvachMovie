package dvachmovie.usecase.moviestorage

import dvachmovie.storage.MovieStorage
import dvachmovie.storage.OnMovieChangedListener
import dvachmovie.usecase.base.UseCase
import javax.inject.Inject

class SetMovieChangedListenerUseCase @Inject constructor(
        private val movieStorage: MovieStorage) : UseCase<OnMovieChangedListener, Unit>() {

    override fun execute(input: OnMovieChangedListener) {
        movieStorage.onMovieChangedListener = input
    }
}
