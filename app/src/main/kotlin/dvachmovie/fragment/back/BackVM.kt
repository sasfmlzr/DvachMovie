package dvachmovie.fragment.back

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dvachmovie.pipe.moviestorage.EraseMovieStoragePipe
import javax.inject.Inject

class BackVM @Inject constructor(
        private val eraseMovieStoragePipe: EraseMovieStoragePipe
) : ViewModel() {
    val imageId by lazy {
        MutableLiveData<Int>()
    }

    fun eraseMovieStorage(){
        eraseMovieStoragePipe.execute(Unit)
    }
}
