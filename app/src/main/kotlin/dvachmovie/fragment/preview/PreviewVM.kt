package dvachmovie.fragment.preview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dvachmovie.db.data.MovieEntity
import dvachmovie.repository.local.MovieStorage
import javax.inject.Inject

class PreviewVM @Inject constructor(movieStorage: MovieStorage) : ViewModel() {
    private val uriMovie = MutableLiveData<List<MovieEntity>>()

    init {
        uriMovie.value = movieStorage.movieList.value?.map { movie ->
            movie
        }
    }

    fun getUriMovie() = uriMovie
}
