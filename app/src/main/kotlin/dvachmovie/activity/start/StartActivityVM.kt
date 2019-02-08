package dvachmovie.activity.start

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class StartActivityVM @Inject constructor() : ViewModel() {
    val initText = MutableLiveData<String>()
    var isInit = true

    init {
        initText.value = "Splash Screen"
    }
}
