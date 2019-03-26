package dvachmovie.repository.local

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import dvachmovie.db.data.MovieEntity
import dvachmovie.repository.db.MovieDBRepository
import dvachmovie.storage.SettingsStorage
import javax.inject.Inject

class MovieRepository @Inject constructor(
        private val movieStorage: MovieStorage,
        private val movieDBRepository: MovieDBRepository,
        private val settingsStorage: SettingsStorage
) {

    var posPlayer = 0
    var isCalculateDiff = true

    fun getPos() = movieStorage.getIndexPosition()

    fun observeDB(lifecycleOwner: LifecycleOwner) {
        isCalculateDiff = true
        movieDBRepository.getMoviesFromBoard(settingsStorage.getBoard())
                .observe(lifecycleOwner, Observer {dbMovies ->
                    if (isCalculateDiff) {
                        val diffList = MovieUtils
                                .calculateDiff(movieStorage.movieList.value!!,
                                        dbMovies) as MutableList

                        if(diffList.isNotEmpty()){
                            diffList.addAll(movieStorage.movieList.value!!)
                            movieStorage.movieList.value = diffList
                        }
                    }
                })
    }

    fun observeDB(lifecycleOwner: LifecycleOwner,
                  observer: Observer<List<MovieEntity>>) {
        movieDBRepository
                .getMoviesFromBoard(settingsStorage.getBoard())
                .observe(lifecycleOwner, observer)
    }

    fun shuffleMovies() {
        movieStorage.movieList.value = MovieUtils.shuffleMovies(movieStorage.movieList.value ?: listOf())
                as MutableList<MovieEntity>
    }
}
