package dvachmovie.storage.local

import androidx.lifecycle.MutableLiveData
import dvachmovie.db.data.Movie
import javax.inject.Inject

class LocalMovieStorage @Inject constructor() : MovieStorage {
    override var movieList: List<Movie> = mutableListOf()
    override val currentMovie: MutableLiveData<Movie> = MutableLiveData()
}
