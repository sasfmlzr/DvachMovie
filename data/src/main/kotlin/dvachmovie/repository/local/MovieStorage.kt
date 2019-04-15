package dvachmovie.repository.local

import androidx.lifecycle.MutableLiveData
import dvachmovie.db.data.MovieEntity

data class MovieStorage(
        val movieList: MutableLiveData<List<MovieEntity>> = MutableLiveData(),
        val currentMovie: MutableLiveData<MovieEntity> = MutableLiveData()) {

    init {
        movieList.value = listOf()
        currentMovie.value = MovieEntity("")
    }

    /** return 0 if element not contained */
    fun getIndexPosition(): Int =
            (movieList.value ?: listOf()).let { list ->
                if (list.contains(currentMovie.value)) {
                    return list.indexOf(currentMovie.value)
                }
                return 0
            }
}
