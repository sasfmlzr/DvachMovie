package dvachmovie.activity.start

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class StartActivityViewModel @Inject constructor(): ViewModel() {
    val initText: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    init {
        initText.value = "Initialization"
    }
}