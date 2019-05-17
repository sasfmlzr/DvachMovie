package dvachmovie.fragment.preview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dvachmovie.db.data.MovieEntity
import dvachmovie.storage.local.MovieStorage
import dvachmovie.usecase.moviestorage.GetCurrentMovieUseCase
import dvachmovie.usecase.moviestorage.GetMovieListUseCase
import javax.inject.Inject

class PreviewVM @Inject constructor(getMovieListUseCase: GetMovieListUseCase,
                                    getCurrentMovieUseCase: GetCurrentMovieUseCase) : ViewModel() {
    val movieList = getMovieListUseCase.getMovieList()
    val currentMovie = getCurrentMovieUseCase.getCurrentMovie()

    private val uriMovie = MutableLiveData<List<MovieEntity>>().apply {
        value = movieList.value?.map {it}
    }

    fun getUriMovie() = uriMovie

    val sdkKey = MutableLiveData("ca-app-pub-3074235676525198~1117408577")
}
