package dvachmovie.fragment.start

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dvachmovie.PresenterModel
import dvachmovie.architecture.ScopeProvider
import dvachmovie.pipe.AmountRequestsModel
import dvachmovie.pipe.CountCompletedRequestsModel
import dvachmovie.pipe.DvachModel
import dvachmovie.pipe.ErrorModel
import dvachmovie.pipe.network.DvachPipe
import dvachmovie.pipe.settingsstorage.PutBoardPipe
import dvachmovie.pipe.settingsstorage.PutCookiePipe
import dvachmovie.storage.local.MovieDBCache
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

open class StartVM @Inject constructor(
        private val broadcastChannel: BroadcastChannel<PresenterModel>,
        private val dvachPipe: DvachPipe,
        putCookiePipe: PutCookiePipe,
        putBoardPipe: PutBoardPipe,
        private val scopeProvider: ScopeProvider) : ViewModel() {

    private lateinit var dvachJob: Job

    val initText = MutableLiveData<String>("Initialization")

    val viewRetryBtn = MutableLiveData<Boolean>(false)

    val imageId by lazy {
        MutableLiveData<Int>()
    }


    val progressLoadingMovies = MutableLiveData<Int>()
    val amountMovies = MutableLiveData<Int>()

    override fun onCleared() {
        if (::dvachJob.isInitialized) {
            dvachJob.cancel()
        }
        viewModelScope.cancel()
        super.onCleared()
    }

    val onButtonStartClicked = View.OnClickListener {
        scopeProvider.ioScope.launch {
            dvachPipe.forceStart()
        }
    }

    val onButtonRetryClicked = View.OnClickListener {
        viewModelScope.launch(Job()) {
            loadNewMovies()
        }
    }

    val onButtonChangeDefaultBoardClicked = View.OnClickListener {
        viewModelScope.launch(Job()) {
            putBoardPipe.execute("b")
            loadNewMovies()
        }
    }

    init {
        viewModelScope.launch {
            //In the future move
            putCookiePipe.execute("92ea293bf47456479e25b11ba67bb17a")

            broadcastChannel.asFlow().collect {
                render(it)
            }
        }
    }

    fun loadNewMovies() {
        viewModelScope.launch {
            viewRetryBtn.value = false
            progressLoadingMovies.value = 0
        }

        dvachJob = scopeProvider.ioScope.launch(Job()) {
            dvachPipe.execute(null)
        }
    }

    private fun render(model: PresenterModel) {
        when (model) {
            is DvachModel -> {
                MovieDBCache.movieList = model.movies
                initDBTask()
                routeToMovieFragmentTask()
            }

            is ErrorModel -> {
                showErrorTask(model.throwable)
                viewRetryBtn.postValue(true)
            }

            is CountCompletedRequestsModel -> progressLoadingMovies.postValue(model.count)

            is AmountRequestsModel -> amountMovies.postValue(model.max)
        }
    }

    lateinit var initDBTask: () -> Unit
    lateinit var routeToMovieFragmentTask: () -> Unit
    lateinit var showErrorTask: (throwable: Throwable) -> Unit
}
