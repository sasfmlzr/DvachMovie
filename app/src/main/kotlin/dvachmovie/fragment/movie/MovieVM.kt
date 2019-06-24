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
import dvachmovie.architecture.ScopeProvider
import dvachmovie.db.data.Movie
import dvachmovie.pipe.ErrorModel
import dvachmovie.pipe.ReportModel
import dvachmovie.pipe.ShuffledMoviesModel
import dvachmovie.pipe.android.MarkCurrentMovieAsPlayedPipe
import dvachmovie.pipe.moviestorage.GetCurrentMoviePipe
import dvachmovie.pipe.moviestorage.GetIndexPosByMoviePipe
import dvachmovie.pipe.moviestorage.GetMovieListPipe
import dvachmovie.pipe.moviestorage.SetCurrentMoviePipe
import dvachmovie.pipe.moviestorage.SetMovieChangedListenerPipe
import dvachmovie.pipe.moviestorage.SetMovieListChangedListenerPipe
import dvachmovie.pipe.moviestorage.SetMovieListPipe
import dvachmovie.pipe.network.GetCookiePipe
import dvachmovie.pipe.network.ReportPipe
import dvachmovie.pipe.settingsstorage.GetIsAllowGesturePipe
import dvachmovie.pipe.settingsstorage.GetIsListBtnVisiblePipe
import dvachmovie.pipe.settingsstorage.GetIsReportBtnVisiblePipe
import dvachmovie.pipe.utils.ShuffleMoviesPipe
import dvachmovie.storage.OnMovieChangedListener
import dvachmovie.storage.OnMovieListChangedListener
import dvachmovie.usecase.real.ReportUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieVM @Inject constructor(
        private val broadcastChannel: BroadcastChannel<PresenterModel>,
        getCookiePipe: GetCookiePipe,
        getMovieListPipe: GetMovieListPipe,
        getCurrentMoviePipe: GetCurrentMoviePipe,
        private val getIsReportBtnVisiblePipe: GetIsReportBtnVisiblePipe,
        private val getIsListBtnVisiblePipe: GetIsListBtnVisiblePipe,
        private val getIsAllowGesturePipe: GetIsAllowGesturePipe,
        shuffleMoviesPipe: ShuffleMoviesPipe,
        reportPipe: ReportPipe,
        private val getIndexPosPipe: GetIndexPosByMoviePipe,
        private val markCurrentMovieAsPlayedPipe: MarkCurrentMovieAsPlayedPipe,
        coroutinesProvider: ScopeProvider,
        setMovieListChangedListenerPipe: SetMovieListChangedListenerPipe,
        setMovieChangedListenerPipe: SetMovieChangedListenerPipe,
        val setCurrentMoviePipe: SetCurrentMoviePipe,
        val setMovieListPipe: SetMovieListPipe) : ViewModel() {

    val isReportBtnVisible = MutableLiveData<Boolean>()

    val isListBtnVisible = MutableLiveData<Boolean>()

    val isGestureEnabled = MutableLiveData<Boolean>()

    init {
        setMovieListChangedListenerPipe.execute(object : OnMovieListChangedListener {
            override fun onListChanged(movies: List<Movie>) {
                movieList.value = movies
            }
        })

        setMovieChangedListenerPipe.execute(object : OnMovieChangedListener {
            override fun onMovieChanged(movie: Movie) {
                currentMovie.value = movie
            }
        })

        viewModelScope.launch {
            broadcastChannel.asFlow().collect {
                render(it)
            }
        }
        refreshVM()
    }

    fun refreshVM() {
        isReportBtnVisible.value = getIsReportBtnVisiblePipe.execute(Unit)

        isListBtnVisible.value = getIsListBtnVisiblePipe.execute(Unit)

        isGestureEnabled.value = getIsAllowGesturePipe.execute(Unit)
    }

    lateinit var downloadBtnClicked: () -> Unit
    lateinit var routeToSettingsTask: () -> Unit
    lateinit var copyURLTask: (movieUrl: String) -> Unit
    lateinit var routeToPreviewTask: () -> Unit
    lateinit var showMessageTask: (message: String) -> Unit

    val onBtnDownloadClicked = View.OnClickListener { downloadBtnClicked() }
    val onBtnShuffleClicked = View.OnClickListener {
        viewModelScope.launch {
            shuffleMoviesPipe.execute(movieList.value ?: listOf())
        }
    }
    val onBtnSettingsClicked = View.OnClickListener { routeToSettingsTask() }

    val onBtnCopyURLClicked = View.OnClickListener {
        copyURLTask(currentMovie.value?.movieUrl ?: "")
    }
    val onBtnListVideosClicked = View.OnClickListener { routeToPreviewTask() }

    val onBtnReportClicked = View.OnClickListener {
        currentMovie.value?.let {
            ReportUseCase.Params(it.board, it.thread, it.post)
        }?.let { model ->
            coroutinesProvider.ioScope.launch(Job()) {
                reportPipe.execute(model)
            }
        }
    }

    val movieList by lazy { MutableLiveData<List<Movie>>(getMovieListPipe.execute(Unit)) }
    val currentMovie by lazy { MutableLiveData<Movie>(getCurrentMoviePipe.execute(Unit)) }

    fun fillCurrentPos() {
        if (currentPos.value == Pair(0, 0L)) {
            currentPos.value = try {
                Pair(getIndexPosPipe.execute(currentMovie.value!!), 0)
            } catch (e: Exception) {
                Pair(0, 0L)
            }
        }
    }


    val currentPos: MutableLiveData<Pair<Int, Long>> by lazy {
        MutableLiveData<Pair<Int, Long>>(Pair(0, 0L))
    }

    val cookie: MutableLiveData<String> by lazy {
        MutableLiveData<String>(getCookiePipe.execute(Unit).toString())
    }

    val isPlayerControlVisibility = MutableLiveData<Boolean>(true)


    private val function = Function<List<Movie>, LiveData<List<Uri>>> { values ->
        val urlVideo: List<Uri> = values.map { value -> Uri.parse(value.movieUrl) }
        if (urlVideo.isNotEmpty()) {
            cookie.value = getCookiePipe.execute(Unit).toString()
        }
        MutableLiveData<List<Uri>>(urlVideo)
    }

    val uriMovies: MutableLiveData<List<Uri>> =
            Transformations.switchMap(movieList, function)
                    as MutableLiveData<List<Uri>>

    fun markMovieAsPlayed(pos: Int) {
        markCurrentMovieAsPlayedPipe.execute(pos)
    }

    private fun render(model: PresenterModel) {
        when (model) {
            is ShuffledMoviesModel -> setMovieListPipe.execute(model.movies)

            is ReportModel -> {
                showMessageTask("Report submitted!")
            }

            is ErrorModel -> {
                showMessageTask(model.throwable.message ?: "Something went wrong. Please try again")
            }
        }
    }

    override fun onCleared() {
        viewModelScope.cancel()
        super.onCleared()
    }
}
