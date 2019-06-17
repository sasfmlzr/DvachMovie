package dvachmovie.storage.local

import androidx.lifecycle.MutableLiveData
import dvachmovie.db.data.Movie

interface MovieStorage {
    var movieList: List<Movie>
    val currentMovie: MutableLiveData<Movie>
}
