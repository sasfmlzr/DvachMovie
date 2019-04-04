package dvachmovie.fragment.movie

import android.net.Uri
import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import dvachmovie.api.CookieManager
import dvachmovie.db.data.MovieEntity
import dvachmovie.repository.local.MovieStorage
import javax.inject.Inject

class MovieVM @Inject constructor(movieStorage: MovieStorage,
                                  cookieManager: CookieManager) : ViewModel() {

    val currentPos: MutableLiveData<Pair<Int, Long>> by lazy {
        MutableLiveData<Pair<Int, Long>>(Pair(0, 0L))
    }

    val cookie: MutableLiveData<String> by lazy {
        MutableLiveData<String>(cookieManager.getCookie().toString())
    }

    val isPlayerControlVisibility = MutableLiveData<Boolean>(true)

    private val function = Function<List<MovieEntity>, LiveData<List<Uri>>> { values ->
        val urlVideo: List<Uri> = values.map { value -> Uri.parse(value.movieUrl) }
        if (urlVideo.isNotEmpty()) {
            cookie.value = cookieManager.getCookie().toString()
        }
        MutableLiveData<List<Uri>>(urlVideo)
    }

    val uriMovies: MutableLiveData<List<Uri>> =
            Transformations.switchMap(movieStorage.movieList, function)
                    as MutableLiveData<List<Uri>>
}
