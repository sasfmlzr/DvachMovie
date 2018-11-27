package dvachmovie.fragment.back

import android.graphics.drawable.Drawable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BackViewModel : ViewModel() {
    val imageView: MutableLiveData<Drawable> by lazy {
        MutableLiveData<Drawable>()
    }
}