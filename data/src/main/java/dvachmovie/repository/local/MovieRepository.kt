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
    var isCalculateDiff = true

    fun getCurrent() = movieStorage.currentMovie

    fun getPos() = movieStorage.getIndexPosition()

    fun getMovies() = movieStorage.movieList

    fun observe(lifecycleOwner: LifecycleOwner) {
        movieDBRepository.getAll().observe(lifecycleOwner, Observer {
            val list = mutableListOf<MovieEntity>()
            if (isCalculateDiff) {
                list.addAll(calculateDiff(movieStorage.movieList.value!!, it as MutableList<MovieEntity>))
                if (list.size != 0) {
                    val movieTempList = movieStorage.movieList.value
                    movieTempList!!.addAll(list)
                    movieStorage.movieList.value = movieTempList
                }
            }
        })
    }

    private fun calculateDiff(localList: MutableList<MovieEntity>,
                              dbList: MutableList<MovieEntity>):
            MutableList<MovieEntity> {
        val result = mutableListOf<MovieEntity>()

        localList.map { movie ->
            dbList.contains(movie).let {
                dbList.remove(movie)
            }
        }

        dbList.map { movie ->
            var equals = false
            localList.map { localMovie ->
                if (localMovie.movieUrl == movie.movieUrl) {
                    equals = true
                }
            }
            if (!equals && movie.isPlayed == 0) {
                result.add(movie)
            }
        }

        return result
    }

    fun shuffle(localList: MutableList<MovieEntity>): MutableList<MovieEntity> {
        val result = mutableListOf<MovieEntity>()
        localList.map {
            if (it.isPlayed == 0) {
                result.add(it)
            }
        }
        return result.shuffled() as MutableList<MovieEntity>
    }
}