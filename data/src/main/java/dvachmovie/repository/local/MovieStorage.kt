package dvachmovie.repository.local

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import dvachmovie.db.data.MovieEntity
import javax.inject.Singleton

@Singleton
data class MovieStorage(
        var movieList: MediatorLiveData<MutableList<MovieEntity>> = MediatorLiveData(),
        var currentMovie: MutableLiveData<MovieEntity> = MutableLiveData()) {

    init {
        movieList.value = mutableListOf()
        currentMovie.value = MovieEntity("")
    }

    fun getIndexPosition(): Int {
        var pos = 0
        if (movieList.value!!.contains(currentMovie.value)) {
            pos = movieList.value!!.indexOf(currentMovie.value)
        }
        return pos
    }
}