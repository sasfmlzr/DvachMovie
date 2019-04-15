package dvachmovie.repository.local

import androidx.lifecycle.MutableLiveData
import dvachmovie.db.data.MovieEntity
import javax.inject.Singleton

data class MovieStorage(
        var movieList: MutableLiveData<List<MovieEntity>> = MutableLiveData(),
        var currentMovie: MutableLiveData<MovieEntity> = MutableLiveData()) {

    init {
        movieList.value = listOf()
        currentMovie.value = MovieEntity("")
    }

    /** return 0 if element not contained */
    fun getIndexPosition(): Int {
        val value = movieList.value ?: listOf()
        if (value.contains(currentMovie.value)) {
            return value.indexOf(currentMovie.value)
        }
        return 0
    }
}
