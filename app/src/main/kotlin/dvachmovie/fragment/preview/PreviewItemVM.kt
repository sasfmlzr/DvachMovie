package dvachmovie.fragment.preview

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class PreviewItemVM(movie: String) : ViewModel() {
    val imageUrl = ObservableField<String>(movie)
}