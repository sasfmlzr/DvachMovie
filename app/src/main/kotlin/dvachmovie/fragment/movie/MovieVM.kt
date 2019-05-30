package dvachmovie.fragment.movie

import android.net.Uri
import android.view.View
import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dvachmovie.PresenterModel
import dvachmovie.db.data.Movie
import dvachmovie.pipe.CookieModel
import dvachmovie.pipe.ShuffledMoviesModel
import dvachmovie.pipe.settingsStorage.GetIsAllowGesturePipe
import dvachmovie.pipe.settingsStorage.GetIsListBtnVisiblePipe
import dvachmovie.pipe.settingsStorage.GetIsReportBtnVisiblePipe
import dvachmovie.pipe.settingsStorage.IsAllowGestureModel
import dvachmovie.pipe.settingsStorage.IsListBtnVisibleModel
import dvachmovie.pipe.settingsStorage.IsReportBtnVisibleModel
import dvachmovie.pipe.utils.ShuffleMoviesPipe
import dvachmovie.usecase.moviestorage.GetCurrentMovieUseCase
import dvachmovie.usecase.moviestorage.GetMovieListUseCase
import dvachmovie.usecase.real.GetCookieUseCase
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class MovieVM @Inject constructor(
        private val broadcastChannel: BroadcastChannel<PresenterModel>,
        getCookieUseCase: GetCookieUseCase,
        getMovieListUseCase: GetMovieListUseCase,
        getCurrentMovieUseCase: GetCurrentMovieUseCase,
        getIsReportBtnVisiblePipe: GetIsReportBtnVisiblePipe,
        getIsListBtnVisiblePipe: GetIsListBtnVisiblePipe,
        getIsAllowGesturePipe: GetIsAllowGesturePipe,
        shuffleMoviesPipe: ShuffleMoviesPipe) : ViewModel() {


    override fun onCleared() {
        viewModelScope.cancel()
        super.onCleared()
    }

    init {
        viewModelScope.launch {
            getIsReportBtnVisiblePipe.execute(Unit)
            getIsListBtnVisiblePipe.execute(Unit)
            getIsAllowGesturePipe.execute(Unit)
            broadcastChannel.asFlow().collect {
                render(it)
            }
        }
    }

    lateinit var downloadBtnClicked: () -> Unit
    lateinit var downloadTask: (download: String, cookie: String) -> Unit
    lateinit var routeToSettingsTask: () -> Unit
    lateinit var copyURLTask: (movieUrl: String) -> Unit
    lateinit var routeToPreviewTask: () -> Unit


    val onBtnDownloadClicked = View.OnClickListener { downloadBtnClicked() }
    val onBtnShuffleClicked = View.OnClickListener {
        shuffleMoviesPipe.execute(movieList.value ?: listOf())
    }
    val onBtnSettingsClicked = View.OnClickListener { routeToSettingsTask() }

    val onBtnCopyURLClicked = View.OnClickListener {
        copyURLTask(currentMovie.value?.movieUrl ?: "")
    }
    val onBtnListVideosClicked = View.OnClickListener { routeToPreviewTask() }
    //val onBtnReportClicked = View.OnClickListener { routeToSettingsTask() }

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

    private fun render(model: PresenterModel) {
        when (model) {
            is CookieModel -> {
                downloadTask(currentMovie.value?.movieUrl ?: "", model.cookie.toString())
            }

            is ShuffledMoviesModel -> {
                movieList.postValue(model.movies)
            }

            is IsListBtnVisibleModel -> {
                isListBtnVisible.postValue(model.value)
            }

            is IsAllowGestureModel -> {
                isGestureEnabled.postValue(model.value)
            }

            is IsReportBtnVisibleModel -> {
                isReportBtnVisible.postValue(model.value)
            }
        }
    }
}
