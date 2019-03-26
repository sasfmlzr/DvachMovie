package dvachmovie.repository.local

import androidx.lifecycle.MutableLiveData
import dvachmovie.db.data.MovieEntity
import javax.inject.Singleton

@Singleton
data class MovieStorage(
        var movieList: MutableLiveData<MutableList<MovieEntity>> = MutableLiveData(),
        var currentMovie: MutableLiveData<MovieEntity> = MutableLiveData()) {

    init {
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
