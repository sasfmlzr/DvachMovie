package dvachmovie.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import dvachmovie.db.data.MovieEntity
import dvachmovie.repository.db.MovieDBRepository
import dvachmovie.storage.local.MovieStorage
import dvachmovie.storage.SettingsStorage
import javax.inject.Inject

class MovieObserver @Inject constructor(
        private val movieStorage: MovieStorage,
        private val movieDBRepository: MovieDBRepository,
        private val settingsStorage: SettingsStorage
) {
    fun observeDB(lifecycleOwner: LifecycleOwner) {
        movieDBRepository.getMoviesFromBoard(settingsStorage.getBoard())
                .observe(lifecycleOwner, Observer { dbMovies ->
                    val movieList = movieStorage.movieList.value ?: listOf()
                    val diffList = MovieUtils.calculateDiff(movieList,
                            dbMovies)

                    if (diffList.isNotEmpty()) {
                        movieStorage.movieList.value = diffList + movieList
                    }
                })
    }

    fun observeDB(lifecycleOwner: LifecycleOwner,
                  observer: Observer<List<MovieEntity>>) {
        movieDBRepository
                .getMoviesFromBoard(settingsStorage.getBoard())
                .observe(lifecycleOwner, observer)
    }

    fun markCurrentMovieAsPlayed(isPlayed: Boolean, indexPosition: Int) {
        movieStorage.currentMovie.value =
                movieStorage.movieList.value?.getOrNull(indexPosition).apply {
                    this?.isPlayed = isPlayed
                }
    }
}
