package dvachmovie.fragment.preview

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import dvachmovie.db.data.MovieEntity

class PreviewItemVM(movie: MovieEntity) : ViewModel() {
    val imageUrl = ObservableField<MovieEntity>(movie)
}