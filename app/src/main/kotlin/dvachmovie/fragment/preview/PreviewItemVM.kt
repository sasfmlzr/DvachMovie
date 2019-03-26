package dvachmovie.fragment.preview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dvachmovie.api.Cookie
import dvachmovie.db.data.MovieEntity
import javax.inject.Inject

class PreviewItemVM @Inject constructor(movie: MovieEntity,
                                        cookie: Cookie) : ViewModel() {
    var imageUrl = MutableLiveData<MovieEntity>(movie)
    var cookie = MutableLiveData<Cookie>()

    init {
        this.cookie.value = cookie
    }
}
