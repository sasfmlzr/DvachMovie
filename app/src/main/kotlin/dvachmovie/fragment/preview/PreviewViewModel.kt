package dvachmovie.fragment.preview

import android.graphics.drawable.Drawable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class PreviewViewModel @Inject constructor() : ViewModel() {
    val uriMovie: MutableLiveData<List<Drawable>> by lazy {
        MutableLiveData<List<Drawable>>()
    }

}