package dvachmovie.utils

import dvachmovie.db.data.Movie
import dvachmovie.repository.MovieDBRepository
import dvachmovie.storage.MovieStorage
import dvachmovie.storage.SettingsStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalMovieObserver @Inject constructor(
        private val movieStorage: MovieStorage,
        private val movieDBRepository: MovieDBRepository,
        private val settingsStorage: SettingsStorage,
        private val movieUtils: MovieUtils
) : MovieObserver {
    override suspend fun observeDB() {
        val dbMovies = movieDBRepository.getMoviesFromBoard(settingsStorage.getBoard())

        val movieList = movieStorage.movieList
        val diffList = movieUtils.calculateDiff(movieList, dbMovies)

        if (diffList.isNotEmpty()) {
            withContext(Dispatchers.Main) {
                movieStorage.setMovieListAndUpdate(
                        movieUtils.sortByDate(diffList + movieList))
            }
        }
    }

    override fun observeDB(onGetMovie: OnGetMovieListener) {
        val dbMovies = movieDBRepository
                .getMoviesFromBoard(settingsStorage.getBoard())
        onGetMovie.onGet(dbMovies)
    }

    interface OnGetMovieListener {
        fun onGet(movies: List<Movie>)
    }
}
