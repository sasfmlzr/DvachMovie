package dvachmovie.fragment.preview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dvachmovie.api.Cookie
import dvachmovie.db.data.Movie
import dvachmovie.db.data.MovieEntity
import javax.inject.Inject

class PreviewItemVM @Inject constructor(movie: Movie,
                                        cookie: Cookie) : ViewModel() {
    var imageUrl = MutableLiveData<Movie>(movie)
    var cookie = MutableLiveData<Cookie>(cookie)
}
