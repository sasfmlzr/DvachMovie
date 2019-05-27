package dvachmovie.storage.local

import androidx.lifecycle.MutableLiveData
import dvachmovie.db.data.Movie

interface MovieStorage {
    val movieList: MutableLiveData<List<Movie>>
    val currentMovie: MutableLiveData<Movie>
}
