package dvachmovie.fragment.preview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dvachmovie.db.data.Movie
import dvachmovie.pipe.android.moviestorage.GetCurrentMoviePipe
import dvachmovie.pipe.android.moviestorage.GetMovieListPipe
import javax.inject.Inject

class PreviewVM @Inject constructor(getMovieListPipe: GetMovieListPipe,
                                    getCurrentMoviePipe: GetCurrentMoviePipe) : ViewModel() {
    val movieList by lazy { getMovieListPipe.execute(Unit) }
    val currentMovie by lazy { getCurrentMoviePipe.execute(Unit) }

    val uriMovie = MutableLiveData<List<Movie>>().apply {
        value = movieList.value?.map { it }
    }
}
