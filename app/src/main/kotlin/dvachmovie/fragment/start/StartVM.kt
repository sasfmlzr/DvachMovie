package dvachmovie.fragment.start

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class StartVM @Inject constructor() : ViewModel() {

    val initText = MutableLiveData<String>()
    val viewRetryBtn = MutableLiveData<Boolean>()
    val initGif: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    init {
        initText.value = "Initialization"
        viewRetryBtn.value = false
    }
}
