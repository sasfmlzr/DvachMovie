package dvachmovie.fragment.movie

import android.net.Uri
import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import dvachmovie.api.CookieManager
import dvachmovie.architecture.ScopeProvider
import dvachmovie.db.data.Movie
import dvachmovie.moviestorage.GetCurrentMovieUseCase
import dvachmovie.moviestorage.GetMovieListUseCase
import dvachmovie.usecase.settingsStorage.GetIsAllowGestureUseCase
import dvachmovie.usecase.settingsStorage.GetIsListBtnVisibleUseCase
import dvachmovie.usecase.settingsStorage.GetIsReportBtnVisibleUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieVM @Inject constructor(cookieManager: CookieManager,
                                  getMovieListUseCase: GetMovieListUseCase,
                                  getCurrentMovieUseCase: GetCurrentMovieUseCase,
                                  getIsReportBtnVisibleUseCase: GetIsReportBtnVisibleUseCase,
                                  getIsListBtnVisibleUseCase: GetIsListBtnVisibleUseCase,
                                  getIsAllowGestureUseCase: GetIsAllowGestureUseCase,
                                  scopeProvider: ScopeProvider) : ViewModel() {

    val movieList = getMovieListUseCase.getMovieList()
    val currentMovie = getCurrentMovieUseCase.getCurrentMovie()

    val currentPos: MutableLiveData<Pair<Int, Long>> by lazy {
        MutableLiveData<Pair<Int, Long>>(Pair(0, 0L))
    }

    val cookie: MutableLiveData<String> by lazy {
        MutableLiveData<String>(cookieManager.getCookie().toString())
    }

    val isPlayerControlVisibility = MutableLiveData<Boolean>(true)

    val isReportBtnVisible = MutableLiveData<Boolean>()

    val isListBtnVisible = MutableLiveData<Boolean>()

    private val function = Function<List<Movie>, LiveData<List<Uri>>> { values ->
        val urlVideo: List<Uri> = values.map { value -> Uri.parse(value.movieUrl) }
        if (urlVideo.isNotEmpty()) {
            cookie.value = cookieManager.getCookie().toString()
        }
        MutableLiveData<List<Uri>>(urlVideo)
    }

    val uriMovies: MutableLiveData<List<Uri>> =
            Transformations.switchMap(movieList, function)
                    as MutableLiveData<List<Uri>>

    val isGestureEnabled = MutableLiveData<Boolean>()

    init {
        scopeProvider.uiScope.launch {
            isGestureEnabled.value = getIsAllowGestureUseCase.execute(Unit)
            isReportBtnVisible.value = getIsReportBtnVisibleUseCase.execute(Unit)
            isListBtnVisible.value = getIsListBtnVisibleUseCase.execute(Unit)
        }
    }
}
