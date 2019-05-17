package dvachmovie.storage.local

import androidx.lifecycle.MutableLiveData
import dvachmovie.db.data.MovieEntity
import dvachmovie.utils.MovieUtils

data class MovieStorage(
        val movieList: MutableLiveData<List<MovieEntity>> = MutableLiveData(),
        val currentMovie: MutableLiveData<MovieEntity> = MutableLiveData(),
        val movieIntentFilterEntity: MutableLiveData<List<MovieEntity>> = MutableLiveData()) {

    init {
        movieList.value = listOf()
    }

    fun getIndexPosition(): Int = MovieUtils.getIndexPosition(currentMovie.value!!,
            movieList.value ?: listOf())
}
