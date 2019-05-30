package dvachmovie.fragment.start

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dvachmovie.PresenterModel
import dvachmovie.pipe.AmountRequestsModel
import dvachmovie.pipe.CountCompletedRequestsModel
import dvachmovie.pipe.DvachModel
import dvachmovie.pipe.DvachPipe
import dvachmovie.pipe.ErrorModel
import dvachmovie.storage.local.MovieDBCache
import dvachmovie.usecase.settingsStorage.PutBoardUseCase
import dvachmovie.usecase.settingsStorage.PutCookieUseCase
import dvachmovie.worker.WorkerManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

open class StartVM @Inject constructor(
        private var broadcastChannel: BroadcastChannel<PresenterModel>,
        private var dvachPipe: DvachPipe,
        putCookieUseCase: PutCookieUseCase,
        putBoardUseCase: PutBoardUseCase) : ViewModel() {

    val initText = MutableLiveData<String>("Initialization")

    val viewRetryBtn = MutableLiveData<Boolean>(false)

    val imageId by lazy {
        MutableLiveData<Int>()
    }

    val progressLoadingMovies = MutableLiveData<Int>()
    val amountMovies = MutableLiveData<Int>()

    override fun onCleared() {
        viewModelScope.cancel()
        super.onCleared()
    }

    val onButtonStartClicked = View.OnClickListener { dvachPipe.forceStart() }

    val onButtonRetryClicked = View.OnClickListener {
        viewModelScope.launch(Job()) {
            loadNewMovies()
        }
    }

    val onButtonChangeDefaultBoardClicked = View.OnClickListener {
        viewModelScope.launch(Job()) {
            putBoardUseCase.execute("b")
            loadNewMovies()
        }
    }

    init {
        viewModelScope.launch {
            //In the future move
            putCookieUseCase.execute("92ea293bf47456479e25b11ba67bb17a")

            broadcastChannel.asFlow().collect {
                render(it)
            }
        }
    }

    fun loadNewMovies() {
        viewRetryBtn.value = false
        progressLoadingMovies.value = 0
        dvachPipe.execute(Unit)
    }

    private fun render(model: PresenterModel) {
        when (model) {
            is DvachModel -> {

                MovieDBCache.movieList = model.movies
                WorkerManager.initDB()
                routeTask()
            }

            is ErrorModel -> {
                errorTask(model.throwable)

                viewRetryBtn.postValue(true)
            }

            is CountCompletedRequestsModel -> progressLoadingMovies.postValue(model.count)

            is AmountRequestsModel -> amountMovies.postValue(model.max)
        }
    }

    lateinit var routeTask: () -> Unit
    lateinit var errorTask: (throwable: Throwable) -> Unit
}
