package dvachmovie.fragment.alonemovie

import android.net.Uri
import android.view.View
import androidx.arch.core.util.Function
import androidx.lifecycle.*
import dvachmovie.PresenterModel
import dvachmovie.pipe.ErrorModel
import dvachmovie.pipe.network.GetCookiePipe
import dvachmovie.pipe.settingsstorage.GetIsAllowGesturePipe
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class AloneMovieVM @Inject constructor(
        private val broadcastChannel: BroadcastChannel<PresenterModel>,
        getCookiePipe: GetCookiePipe,
        private val getIsAllowGesturePipe: GetIsAllowGesturePipe) : ViewModel() {

    val isGestureEnabled = MutableLiveData<Boolean>()

    init {

        viewModelScope.launch {
            broadcastChannel.asFlow().collect {
                render(it)
            }
        }
        refreshVM()
    }

    private fun refreshVM() {
        isGestureEnabled.value = getIsAllowGesturePipe.execute(Unit)
    }

    lateinit var downloadBtnClicked: () -> Unit
    lateinit var copyURLTask: (movieUrl: String) -> Unit
    lateinit var showMessageTask: (message: String) -> Unit
    lateinit var getUrlTask: () -> String

    val onBtnDownloadClicked = View.OnClickListener {
        downloadBtnClicked()
    }

    val onBtnCopyURLClicked = View.OnClickListener {
        copyURLTask(currentMovie.value.orEmpty())
    }

    val currentMovie by lazy {
        MutableLiveData(getUrlTask())
    }

    fun fillCurrentPos() {
        currentPos.value = Pair(0, 0L)
    }

    val currentPos: MutableLiveData<Pair<Int, Long>> by lazy {
        MutableLiveData(Pair(0, 0L))
    }

    val cookie: MutableLiveData<String> by lazy {
        MutableLiveData(getCookiePipe.execute(Unit).toString())
    }

    val isPlayerControlVisibility = MutableLiveData(true)

    private val function = Function<String, LiveData<List<Uri>>> { value ->
        cookie.value = getCookiePipe.execute(Unit).toString()

        MutableLiveData(listOf(Uri.parse(value)))
    }

    val uriMovies: MutableLiveData<List<Uri>> by lazy {
        Transformations.switchMap(currentMovie, function)
                as MutableLiveData<List<Uri>>
    }

    private fun render(model: PresenterModel) {
        when (model) {
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
