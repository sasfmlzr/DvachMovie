package dvachmovie.storage

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

    fun isLoadingEveryTime(): Boolean {
        if (pref.getBoolean(LOADING_PARAM) != null) {
            return pref.getBoolean(LOADING_PARAM)!!
        }
        return false
    }

    fun putLoadingEveryTime(value: Boolean) {
        val job = SupervisorJob()
        val scope = CoroutineScope(Dispatchers.Default + job)

        scope.launch {
            pref.putBoolean(LOADING_PARAM, value)
        }
    }
}
