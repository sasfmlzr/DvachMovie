package dvachmovie.moviestorage

import dvachmovie.storage.local.MovieStorage
import javax.inject.Inject

class GetMovieListUseCase @Inject constructor(private val movieStorage: MovieStorage){

    fun getMovieList() = movieStorage.movieList
}
