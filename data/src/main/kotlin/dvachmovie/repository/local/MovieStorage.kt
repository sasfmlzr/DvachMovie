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

    fun getIndexPosition(): Int = MovieUtils.getIndexPosition(currentMovie.value!!,
            movieList.value ?: listOf())
}
