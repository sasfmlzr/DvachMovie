package dvachmovie.storage.local

import androidx.lifecycle.MutableLiveData
import dvachmovie.db.data.MovieEntity

data class MovieStorage(
        val movieList: MutableLiveData<List<MovieEntity>> = MutableLiveData(),
        val currentMovie: MutableLiveData<MovieEntity> = MutableLiveData())
