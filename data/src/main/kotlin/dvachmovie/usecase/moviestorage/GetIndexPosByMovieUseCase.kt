package dvachmovie.usecase.moviestorage

import dvachmovie.db.data.MovieEntity
import dvachmovie.storage.local.MovieStorage
import dvachmovie.utils.MovieUtils
import javax.inject.Inject

class GetIndexPosByMovieUseCase @Inject constructor(private val movieStorage: MovieStorage){

    fun getIndexPosByMovie(movie: MovieEntity?) = MovieUtils.getIndexPosition(movie,
            movieStorage.movieList.value)
}
