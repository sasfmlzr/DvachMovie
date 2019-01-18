package dvachmovie.storage

import dvachmovie.Constants.Companion.LOADING_NOT_NEEDED
import dvachmovie.storage.base.KeyValueStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

open class SettingsStorage @Inject constructor(
        private val pref: KeyValueStorage
) {

    companion object {
        private const val LOADING_PARAM = "LoadingMoviesOrNot"
    }

    //TODO: LoadingNotNeeded to false
    fun getBoolLoadingParam(): Boolean {
        if (pref.getBoolean(LOADING_PARAM) != null) {
            return pref.getBoolean(LOADING_PARAM)!!
        }
        return LOADING_NOT_NEEDED
    }

    fun putBoolLoadingParam(value: Boolean) {
        val job = SupervisorJob()
        val scope = CoroutineScope(Dispatchers.Default + job)

        scope.launch {
            pref.putBoolean(LOADING_PARAM, value)
        }
    }
}