package dvachmovie.repository.local

import androidx.lifecycle.MutableLiveData
import dvachmovie.db.data.MovieEntity
import javax.inject.Singleton

@Singleton
data class MovieDBCache(
        val movieList: MutableLiveData<List<MovieEntity>> = MutableLiveData())
