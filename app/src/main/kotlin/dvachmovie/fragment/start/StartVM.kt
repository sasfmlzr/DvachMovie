package dvachmovie.fragment.start

import android.graphics.drawable.Drawable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class StartVM @Inject constructor() : ViewModel() {

    val initText =  MutableLiveData<String>()
    val viewRetryBtn = MutableLiveData<Boolean>()
    val initGif : MutableLiveData<Drawable> by lazy {
        MutableLiveData<Drawable>()
    }

    init {
        initText.value = "Initialization"
        viewRetryBtn.value = false
    }
}
