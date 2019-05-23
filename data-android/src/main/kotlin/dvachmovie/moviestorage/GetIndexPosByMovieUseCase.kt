package dvachmovie.moviestorage

import dvachmovie.db.data.Movie
import dvachmovie.storage.local.MovieStorage
import dvachmovie.usecase.base.UseCase
import dvachmovie.utils.MovieUtils
import javax.inject.Inject

class GetIndexPosByMovieUseCase @Inject constructor(
        private val movieStorage: MovieStorage,
        private val movieUtils: MovieUtils) : UseCase<Movie, Int>() {

    override suspend fun execute(input: Movie): Int {
        return movieUtils.getIndexPosition(input,
                movieStorage.movieList.value)
    }
}
