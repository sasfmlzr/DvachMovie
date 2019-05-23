package dvachmovie.fragment.preview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dvachmovie.db.data.Movie
import dvachmovie.moviestorage.GetCurrentMovieUseCase
import dvachmovie.moviestorage.GetMovieListUseCase
import javax.inject.Inject

class PreviewVM @Inject constructor(getMovieListUseCase: GetMovieListUseCase,
                                    getCurrentMovieUseCase: GetCurrentMovieUseCase) : ViewModel() {
    val movieList = getMovieListUseCase.getMovieList()
    val currentMovie = getCurrentMovieUseCase.getCurrentMovie()

    val uriMovie = MutableLiveData<List<Movie>>().apply {
        value = movieList.value?.map { it }
    }

    val sdkKey = MutableLiveData("ca-app-pub-3074235676525198~1117408577")
}
