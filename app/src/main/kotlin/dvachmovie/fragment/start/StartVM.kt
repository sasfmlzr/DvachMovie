package dvachmovie.fragment.start

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dvachmovie.PresenterModel
import dvachmovie.pipe.DvachModel
import dvachmovie.pipe.ErrorModel
import dvachmovie.storage.local.MovieDBCache
import dvachmovie.worker.WorkerManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

open class StartVM @Inject constructor(var broadcastChannel: BroadcastChannel<PresenterModel>) : ViewModel() {
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

    }

fun subscribe(){
  val Job =  viewModelScope.launch() {
        broadcastChannel.asFlow().collect {
            render(it)
        }
    }
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
        }
    }
}
