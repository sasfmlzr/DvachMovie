package dvachmovie.activity.start

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StartActivityViewModel : ViewModel() {
    val initText: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    init {
        initText.value = "Initialization"
    }
}