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

            val list =calculateDiff(movieStorage.movieList.value!!, it as MutableList<MovieEntity>)
            if (list.size!=0) {
                val movieTempList = movieStorage.movieList.value
                movieTempList!!.addAll(list)
                movieStorage.movieList.value = movieTempList
            }
        })
    }

    private fun calculateDiff(localList: MutableList<MovieEntity>,
                              dbList: MutableList<MovieEntity>) :
            MutableList<MovieEntity> {
        val movieList = dbList
        val result = mutableListOf<MovieEntity>()

        localList.map { movie ->
            movieList.contains(movie).let {
                movieList.remove(movie)
            }
        }

        movieList.map { movie ->
            var equals = false
            localList.map { localMovie ->
                if (localMovie.movieUrl == movie.movieUrl) {
                    equals = true
                }
            }
            if (!equals) {
                result.add(movie)
            }
        }
        return result
    }


}