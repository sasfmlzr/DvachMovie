package dvachmovie.activity.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dvachmovie.AppConfig
import dvachmovie.pipe.db.DeleteOldMoviesPipe
import dvachmovie.pipe.settingsstorage.GetCurrentBaseUrlPipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieActivityVM @Inject constructor() : ViewModel() {

    @Inject
    lateinit var getCurrentBaseUrlPipe: GetCurrentBaseUrlPipe

    @Inject
    lateinit var deleteOldMoviesPipe: DeleteOldMoviesPipe

    fun initCurrentBaseUrl() {
        AppConfig.currentBaseUrl = getCurrentBaseUrlPipe.execute(Unit)

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                deleteOldMoviesPipe.execute(Unit)
            }
        }
    }
}
