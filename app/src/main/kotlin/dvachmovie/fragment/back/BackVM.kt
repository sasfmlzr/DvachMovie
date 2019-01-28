package dvachmovie.fragment.back

import android.graphics.drawable.Drawable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class BackVM @Inject constructor() : ViewModel() {
    val imageView = MutableLiveData<Drawable>()
}
