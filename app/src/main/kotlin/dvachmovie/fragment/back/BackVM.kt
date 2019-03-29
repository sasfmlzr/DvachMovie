package dvachmovie.fragment.back

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class BackVM @Inject constructor() : ViewModel() {
    val imageId by lazy {
        MutableLiveData<Int>()
    }
}
