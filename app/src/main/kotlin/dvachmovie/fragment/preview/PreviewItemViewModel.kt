package dvachmovie.fragment.preview

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import dvachmovie.repository.local.Movie

class PreviewItemViewModel(movie: Movie) : ViewModel() {
    val imageUrl = ObservableField<String>(movie.moviePreviewUrl)
}