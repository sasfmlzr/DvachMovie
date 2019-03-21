package dvachmovie.fragment.preview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dvachmovie.api.CookieManager
import dvachmovie.db.data.MovieEntity
import javax.inject.Inject

class PreviewItemVM @Inject constructor(movie: MovieEntity,
                                        cookie: CookieManager.Cookie) : ViewModel() {
    var imageUrl = MutableLiveData<MovieEntity>(movie)
    var cookie = MutableLiveData<CookieManager.Cookie>()

    init {
        this.cookie.value = cookie
    }
}
