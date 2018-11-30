package dvachmovie.repository.local

import androidx.lifecycle.MutableLiveData
import javax.inject.Singleton

@Singleton
data class MovieTempRepository(var movieList: MutableLiveData<MutableList<Movie>> = MutableLiveData(),
                               var currentMovie: MutableLiveData<Movie> = MutableLiveData()) {

    init {
        movieList.value = mutableListOf()
        currentMovie.value = Movie()
    }

    fun getIndexPosition(): Int {
        var pos = 0
        if (movieList.value!!.contains(currentMovie.value)) {
            pos = movieList.value!!.indexOf(currentMovie.value)
        }
        return pos
    }
}