package dvachmovie.usecase.moviestorage

import androidx.lifecycle.MutableLiveData
import dvachmovie.db.data.Movie
import dvachmovie.storage.local.MovieStorage
import dvachmovie.usecase.base.UseCase
import javax.inject.Inject

open class GetCurrentMovieUseCase @Inject constructor(
        private val movieStorage: MovieStorage) : UseCase<Unit, MutableLiveData<Movie>>() {

    override fun execute(input: Unit): MutableLiveData<Movie> = movieStorage.currentMovie
}
