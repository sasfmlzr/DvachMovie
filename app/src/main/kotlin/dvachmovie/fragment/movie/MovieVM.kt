package dvachmovie.fragment.movie

import android.net.Uri
import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import dvachmovie.api.CookieManager
import dvachmovie.db.data.MovieEntity
import dvachmovie.repository.local.MovieRepository
import javax.inject.Inject

class MovieVM @Inject constructor(movieRepository: MovieRepository,
                                  private val cookieManager: CookieManager) : ViewModel() {

    private val movies: MutableLiveData<MutableList<MovieEntity>> = movieRepository.getMovies()

    val currentPos = MutableLiveData<Pair<Int, Long>>()

    val cookie: MutableLiveData<String> by lazy {
        MutableLiveData<String>(cookieManager.cookie.toString())
    }

    private val function = Function<MutableList<MovieEntity>, LiveData<List<Uri>>> { values ->
        val urlVideo: List<Uri> = values.map { value -> Uri.parse(value.movieUrl) }
        if (urlVideo.isNotEmpty())
            cookie.value = cookieManager.cookie.toString()
        MutableLiveData<List<Uri>>(urlVideo)
    }

    val uriMovies: MutableLiveData<List<Uri>> =
            Transformations.switchMap(movies, function) as MutableLiveData<List<Uri>>

    fun getMoviesList() = movies

    val isPlayerControlVisibility: MutableLiveData<Boolean> = MutableLiveData()

    init {
        isPlayerControlVisibility.value = true
    }
}
