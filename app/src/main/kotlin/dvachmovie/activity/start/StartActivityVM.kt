package dvachmovie.activity.start

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dvachmovie.BuildConfig
import javax.inject.Inject

class StartActivityVM @Inject constructor() : ViewModel() {
    val initText = MutableLiveData<String>("Preparing...")
    val version = MutableLiveData<String>(BuildConfig.VERSION_NAME)
}
