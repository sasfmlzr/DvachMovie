package dvachmovie.fragment.preview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dvachmovie.db.data.Movie
import dvachmovie.pipe.moviestorage.GetCurrentMoviePipe
import dvachmovie.pipe.moviestorage.GetIndexPosByMoviePipe
import dvachmovie.pipe.moviestorage.GetMovieListPipe
import javax.inject.Inject

class PreviewVM @Inject constructor(getMovieListPipe: GetMovieListPipe,
                                    getCurrentMoviePipe: GetCurrentMoviePipe,
                                    private val getIndexPosByMoviePipe: GetIndexPosByMoviePipe) : ViewModel() {
    private val movieList by lazy { getMovieListPipe.execute(Unit) }
    private val currentMovie by lazy { getCurrentMoviePipe.execute(Unit) }

    fun getPosCurrentMovie(): Int =
                getIndexPosByMoviePipe.execute(currentMovie)

    val uriMovie = MutableLiveData<List<Movie>>().apply {
        value = movieList
    }
}
