package dvachmovie.fragment.start

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
import dvachmovie.usecase.real.DvachUseCase
import dvachmovie.usecase.settingsStorage.GetBoardUseCase
import dvachmovie.worker.WorkerManager
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

open class StartVM @Inject constructor(
        private var broadcastChannel: BroadcastChannel<PresenterModel>,
        private var dvachPipe: DvachPipe,
        private var getBoardUseCase: GetBoardUseCase) : ViewModel() {
    val initText = MutableLiveData<String>("Initialization")
    val viewRetryBtn = MutableLiveData<Boolean>(false)
    val imageId by lazy {
        MutableLiveData<Int>()
    }

    override fun onCleared() {
        viewModelScope.cancel()

        super.onCleared()
    }


    init {
        viewModelScope.launch {
            loadNewMovies()
            broadcastChannel.asFlow().collect {
                render(it)
            }
        }
    }

    val progressLoadingMovies = MutableLiveData<Int>()
    val amountMovies = MutableLiveData<Int>()

    private suspend fun loadNewMovies() {
        val inputModel = DvachUseCase.Params(getBoardUseCase.execute(Unit))
        dvachPipe.execute(inputModel)
    }

    lateinit var routeTask: () -> Unit
    lateinit var errorTask: (throwable: Throwable) -> Unit

    protected fun render(model: PresenterModel) {
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
}
