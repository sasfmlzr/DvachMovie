package dvachmovie.utils

import dvachmovie.db.data.Movie
import dvachmovie.repository.MovieDBRepository
import dvachmovie.storage.SettingsStorage
import javax.inject.Inject

class LocalMovieObserver @Inject constructor(
        private val movieDBRepository: MovieDBRepository,
        private val settingsStorage: SettingsStorage
) : MovieObserver {
    override suspend fun observeDB() {

    }

    override suspend fun observeDB(onGetMovie: OnGetMovieListener) {
        val dbMovies = movieDBRepository
                .getMoviesFromBoard(settingsStorage.getBoard())
        onGetMovie.onGet(dbMovies)
    }

    interface OnGetMovieListener {
        fun onGet(movies: List<Movie>)
    }
}
