package dvachmovie.fragment.preview

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import dvachmovie.repository.local.Movie

class PreviewItemVM(movie: Movie) : ViewModel() {
    val imageUrl = ObservableField<Movie>(movie)
}