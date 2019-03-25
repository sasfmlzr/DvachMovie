package dvachmovie.repository.local

import androidx.lifecycle.MutableLiveData
import dvachmovie.db.data.MovieEntity
import javax.inject.Singleton

@Singleton
data class MovieDBCache(var movieList: MutableLiveData<MutableList<MovieEntity>> = MutableLiveData())
