package dvachmovie.activity.start

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class StartActivityViewModel : ViewModel() {
    val initText: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    init {
        initText.value = "Initialization"
    }


}