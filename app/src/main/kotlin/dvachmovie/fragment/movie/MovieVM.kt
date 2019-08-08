package dvachmovie.fragment.movie

import android.net.Uri
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dvachmovie.AppConfig
import dvachmovie.PresenterModel
import dvachmovie.R
import dvachmovie.architecture.ScopeProvider
import dvachmovie.db.data.Movie
import dvachmovie.db.data.Thread
import dvachmovie.pipe.ErrorModel
import dvachmovie.pipe.ReportModel
import dvachmovie.pipe.ShuffledMoviesModel
import dvachmovie.pipe.db.GetThreadFromDBByNumPipe
import dvachmovie.pipe.moviestorage.GetCurrentMoviePipe
import dvachmovie.pipe.moviestorage.GetIndexPosByMoviePipe
import dvachmovie.pipe.moviestorage.GetMovieListPipe
import dvachmovie.pipe.moviestorage.MarkCurrentMovieAsPlayedPipe
import dvachmovie.pipe.moviestorage.SetCurrentMoviePipe
import dvachmovie.pipe.moviestorage.SetMovieChangedListenerPipe
import dvachmovie.pipe.moviestorage.SetMovieListChangedListenerPipe
import dvachmovie.pipe.moviestorage.SetMovieListPipe
import dvachmovie.pipe.network.GetCookiePipe
import dvachmovie.pipe.network.ReportPipe
import dvachmovie.pipe.settingsstorage.GetCurrentBaseUrlPipe
import dvachmovie.pipe.settingsstorage.GetIsAllowGesturePipe
import dvachmovie.pipe.settingsstorage.GetIsListBtnVisiblePipe
import dvachmovie.pipe.settingsstorage.GetIsReportBtnVisiblePipe
import dvachmovie.pipe.utils.ShuffleMoviesPipe
import dvachmovie.storage.OnMovieChangedListener
import dvachmovie.storage.OnMovieListChangedListener
import dvachmovie.usecase.real.dvach.ReportUseCase
import dvachmovie.worker.WorkerManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
        private val setMovieListPipe: SetMovieListPipe,
        private val getCurrentBaseUrlPipe: GetCurrentBaseUrlPipe,
        private val getThreadsFromDBByNumPipe: GetThreadFromDBByNumPipe) : ViewModel() {

    val isReportBtnVisible = MutableLiveData<Boolean>()

    val isListBtnVisible = MutableLiveData<Boolean>()

    val isGestureEnabled = MutableLiveData<Boolean>()

    val isHideThreadBtnVisible = MutableLiveData(true)

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

    fun getCurrentBaseUrl() = getCurrentBaseUrlPipe.execute(Unit)

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

    val onBtnHideThreadClicked = View.OnClickListener {
        viewModelScope.launch {
            val thread: Thread? = withContext(Dispatchers.IO) {
                getThreadsFromDBByNumPipe.execute(Pair(currentMovie.value!!.thread, AppConfig.currentBaseUrl))
            }
            if (thread == null) {
                showMessageTask("Please refresh your movies")
            } else {
                AlertDialog.Builder(it.context, R.style.AlertDialogStyle)
                        .setTitle("Confirmation")
                        .setMessage("Thread: \"${thread.nameThread}\" will be hidden")
                        .setPositiveButton("Ok") { _, _ ->
                            WorkerManager.markThreadAsHiddenInDB(it.context, currentMovie.value!!.thread)
                        }
                        .setNegativeButton("Cancel") { _, _ -> }
                        .show()
            }
        }
    }

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
        if (currentPos.value == Pair(0, 0L) || PlayerCache.isHideMovieByThreadTask) {
            currentPos.value = try {
                Pair(getIndexPosPipe.execute(currentMovie.value!!), 0)
            } catch (e: Exception) {
                Pair(0, 0L)
            }
        }
    }

    val currentPos: MutableLiveData<Pair<Int, Long>> by lazy {
        MutableLiveData(Pair(0, 0L))
    }

    val cookie: MutableLiveData<String> by lazy {
        MutableLiveData(getCookiePipe.execute(Unit).toString())
    }

    val isPlayerControlVisibility = MutableLiveData(true)


    private val function = Function<List<Movie>, LiveData<List<Uri>>> { values ->
        val urlVideo: List<Uri> = values.map { value -> Uri.parse(value.movieUrl) }
        if (urlVideo.isNotEmpty()) {
            cookie.value = getCookiePipe.execute(Unit).toString()
        }
        MutableLiveData(urlVideo)
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
