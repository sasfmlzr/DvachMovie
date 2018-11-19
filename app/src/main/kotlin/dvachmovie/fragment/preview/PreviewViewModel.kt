package dvachmovie.fragment.preview

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.graphics.drawable.Drawable

class PreviewViewModel : ViewModel() {
    val uriMovie: MutableLiveData<List<Drawable>> by lazy {
        MutableLiveData<List<Drawable>>()
    }


}