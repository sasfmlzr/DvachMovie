package dvachmovie.fragment.preview

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import dvachmovie.repository.local.Movie

class PreviewItemViewModel(movie: Movie) : ViewModel() {
    val imageUrl = ObservableField<String>(movie.moviePreviewUrl)
}