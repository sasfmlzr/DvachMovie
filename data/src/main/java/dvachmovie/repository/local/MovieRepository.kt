package dvachmovie.repository.local

import dvachmovie.db.data.MovieEntity
import dvachmovie.repository.db.MovieDBRepository
import javax.inject.Inject

class MovieRepository @Inject constructor(
        private val movieStorage: MovieStorage,
        movieDBRepository: MovieDBRepository
) {

    init {
        movieStorage.movieList.addSource(movieDBRepository.getAll()) {
            movieStorage.movieList.value = it as MutableList<MovieEntity>
        }
    }

    fun getCurrent() = movieStorage.currentMovie

    fun getPos() = movieStorage.getIndexPosition()

    fun setCurrent(movieEntity: MovieEntity){
        movieStorage.currentMovie.value = movieEntity
    }

    fun getMovies() = movieStorage.movieList

}