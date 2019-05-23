package dvachmovie.fragment.preview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dvachmovie.db.data.Movie
import dvachmovie.moviestorage.GetCurrentMovieUseCase
import dvachmovie.moviestorage.GetMovieListUseCase
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class PreviewVM @Inject constructor(getMovieListUseCase: GetMovieListUseCase,
                                    getCurrentMovieUseCase: GetCurrentMovieUseCase) : ViewModel() {
    val movieList by lazy { runBlocking { getMovieListUseCase.execute(Unit) } }
    val currentMovie by lazy { runBlocking { getCurrentMovieUseCase.execute(Unit) } }

    val uriMovie = MutableLiveData<List<Movie>>().apply {
        value = movieList.value?.map { it }
    }

    val sdkKey = MutableLiveData("ca-app-pub-3074235676525198~1117408577")
}
