package dvachmovie.activity.start

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dvachmovie.AppConfig
import dvachmovie.BuildConfig
import dvachmovie.pipe.db.DeleteOldMoviesPipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StartActivityVM @Inject constructor() : ViewModel() {
    val initText = MutableLiveData("Preparing...")
    val version = MutableLiveData(BuildConfig.VERSION_NAME)

    @Inject
    lateinit var deleteOldMoviesPipe: DeleteOldMoviesPipe

     suspend fun removeOldMovies() {
            withContext(Dispatchers.IO) {
                deleteOldMoviesPipe.execute(Unit)
            }
    }
}
