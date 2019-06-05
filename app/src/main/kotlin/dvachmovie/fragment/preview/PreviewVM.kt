package dvachmovie.fragment.preview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dvachmovie.db.data.Movie
import dvachmovie.pipe.android.moviestorage.GetCurrentMoviePipe
import dvachmovie.pipe.android.moviestorage.GetIndexPosByMoviePipe
import dvachmovie.pipe.android.moviestorage.GetMovieListPipe
import javax.inject.Inject

class PreviewVM @Inject constructor(getMovieListPipe: GetMovieListPipe,
                                    getCurrentMoviePipe: GetCurrentMoviePipe,
                                    private val getIndexPosByMoviePipe: GetIndexPosByMoviePipe) : ViewModel() {
    val movieList by lazy { getMovieListPipe.execute(Unit) }
    val currentMovie by lazy { getCurrentMoviePipe.execute(Unit) }

    fun getPosCurrentMovie(): Int =
            currentMovie.value?.let {
                getIndexPosByMoviePipe.execute(it)
            } ?: 0


    val uriMovie = MutableLiveData<List<Movie>>().apply {
        value = movieList.value?.map { it }
    }
}
