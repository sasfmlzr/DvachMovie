package dvachmovie.moviestorage

import androidx.lifecycle.MutableLiveData
import dvachmovie.db.data.Movie
import dvachmovie.storage.local.MovieStorage
import dvachmovie.usecase.base.UseCase
import javax.inject.Inject

class GetMovieListUseCase @Inject constructor(
        private val movieStorage: MovieStorage) : UseCase<Unit, MutableLiveData<List<Movie>>>() {

    override suspend fun execute(input: Unit): MutableLiveData<List<Movie>> {
        return movieStorage.movieList
    }
}
