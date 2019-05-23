package dvachmovie.moviestorage

import androidx.lifecycle.MutableLiveData
import dvachmovie.db.data.Movie
import dvachmovie.storage.local.MovieStorage
import dvachmovie.usecase.base.UseCase
import javax.inject.Inject

class GetCurrentMovieUseCase @Inject constructor(
        private val movieStorage: MovieStorage) : UseCase<Unit, MutableLiveData<Movie>>(){

    override suspend fun execute(input: Unit): MutableLiveData<Movie> {
        return movieStorage.currentMovie
          }
}
