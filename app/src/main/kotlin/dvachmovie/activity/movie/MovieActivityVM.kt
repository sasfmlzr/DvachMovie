package dvachmovie.activity.movie

import androidx.lifecycle.ViewModel
import dvachmovie.AppConfig
import dvachmovie.pipe.settingsstorage.GetCurrentBaseUrlPipe
import javax.inject.Inject

class MovieActivityVM @Inject constructor() : ViewModel() {

    @Inject
    lateinit var getCurrentBaseUrlPipe: GetCurrentBaseUrlPipe

    fun initCurrentBaseUrl(){
        AppConfig.currentBaseUrl = getCurrentBaseUrlPipe.execute(Unit)
    }
}
