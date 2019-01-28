package dvachmovie.repository.local

import androidx.lifecycle.MutableLiveData
import dvachmovie.db.data.MovieEntity
import javax.inject.Singleton

@Singleton
data class MovieCache(var movieList: MutableLiveData<MutableList<MovieEntity>> = MutableLiveData()) {
    init {
        movieList.value = mutableListOf()
    }
}
