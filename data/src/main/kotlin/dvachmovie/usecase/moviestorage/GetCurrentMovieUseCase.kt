package dvachmovie.usecase.moviestorage

import dvachmovie.storage.local.MovieStorage
import javax.inject.Inject

class GetCurrentMovieUseCase @Inject constructor(private val movieStorage: MovieStorage){

    fun getCurrentMovie() = movieStorage.currentMovie
}
