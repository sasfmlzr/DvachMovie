package dvachmovie.repository.local

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import dvachmovie.db.data.MovieEntity
import dvachmovie.repository.db.MovieDBRepository
import javax.inject.Inject

class MovieRepository @Inject constructor(
        private val movieStorage: MovieStorage,
        private val movieDBRepository: MovieDBRepository
) {

    var posPlayer = 0
    fun getCurrent() = movieStorage.currentMovie

    fun getPos() = movieStorage.getIndexPosition()

    fun setCurrent(movieEntity: MovieEntity) {
        movieStorage.currentMovie.value = movieEntity
    }

    fun getMovies() = movieStorage.movieList

    fun observe(lifecycleOwner: LifecycleOwner) {
        movieDBRepository.getAll().observe(lifecycleOwner, Observer {
            movieStorage.movieList.value = it as MutableList<MovieEntity>
        })
    }
}