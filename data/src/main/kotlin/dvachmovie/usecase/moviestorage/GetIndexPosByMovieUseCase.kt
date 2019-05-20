package dvachmovie.usecase.moviestorage

import dvachmovie.db.data.Movie
import dvachmovie.storage.local.MovieStorage
import dvachmovie.utils.MovieUtils
import javax.inject.Inject

class GetIndexPosByMovieUseCase @Inject constructor(private val movieStorage: MovieStorage) {

    fun getIndexPosByMovie(movie: Movie?) = MovieUtils.getIndexPosition(movie,
            movieStorage.movieList.value)
}
