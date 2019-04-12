package dvachmovie.fragment.preview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dvachmovie.db.data.MovieEntity
import dvachmovie.repository.local.MovieStorage
import javax.inject.Inject

class PreviewVM @Inject constructor(movieStorage: MovieStorage) : ViewModel() {
    private val uriMovie = MutableLiveData<List<MovieEntity>>().apply {
        value = movieStorage.movieList.value?.map {it}
    }

    fun getUriMovie() = uriMovie

    val sdkKey = MutableLiveData("ca-app-pub-3074235676525198~3986251123")
}
