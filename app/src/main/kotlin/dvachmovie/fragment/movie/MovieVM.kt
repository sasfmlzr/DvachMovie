package dvachmovie.fragment.movie

import android.net.Uri
import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import dvachmovie.architecture.ScopeProvider
import dvachmovie.db.data.Movie
import dvachmovie.usecase.moviestorage.GetCurrentMovieUseCase
import dvachmovie.usecase.moviestorage.GetMovieListUseCase
import dvachmovie.usecase.real.GetCookieUseCase
import dvachmovie.usecase.settingsStorage.GetIsAllowGestureUseCase
import dvachmovie.usecase.settingsStorage.GetIsListBtnVisibleUseCase
import dvachmovie.usecase.settingsStorage.GetIsReportBtnVisibleUseCase
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class MovieVM @Inject constructor(getCookieUseCase: GetCookieUseCase,
                                  getMovieListUseCase: GetMovieListUseCase,
                                  getCurrentMovieUseCase: GetCurrentMovieUseCase) : ViewModel() {

    val movieList by lazy { runBlocking { getMovieListUseCase.execute(Unit) } }
    val currentMovie by lazy { runBlocking { getCurrentMovieUseCase.execute(Unit) } }

    val currentPos: MutableLiveData<Pair<Int, Long>> by lazy {
        MutableLiveData<Pair<Int, Long>>(Pair(0, 0L))
    }

    val cookie: MutableLiveData<String> by lazy {
        runBlocking {
            MutableLiveData<String>(getCookieUseCase.execute(Unit).toString())
        }
    }

    val isPlayerControlVisibility = MutableLiveData<Boolean>(true)


    private val function = Function<List<Movie>, LiveData<List<Uri>>> { values ->
        val urlVideo: List<Uri> = values.map { value -> Uri.parse(value.movieUrl) }
        if (urlVideo.isNotEmpty()) {
            runBlocking {
                cookie.value = getCookieUseCase.execute(Unit).toString()
            }
        }
        MutableLiveData<List<Uri>>(urlVideo)
    }

    val uriMovies: MutableLiveData<List<Uri>> =
            Transformations.switchMap(movieList, function)
                    as MutableLiveData<List<Uri>>

    val isReportBtnVisible = MutableLiveData<Boolean>()

    val isListBtnVisible = MutableLiveData<Boolean>()

    val isGestureEnabled = MutableLiveData<Boolean>()
}
