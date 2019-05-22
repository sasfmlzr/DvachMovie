package dvachmovie.storage.local

import androidx.lifecycle.MutableLiveData
import dvachmovie.db.data.Movie
import javax.inject.Singleton

@Singleton
data class MovieDBCache(
        val movieList: MutableLiveData<List<Movie>> = MutableLiveData())
