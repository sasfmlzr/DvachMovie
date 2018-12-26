package dvachmovie.usecase

import dvachmovie.Constraints.Companion.LOADING_NOT_NEEDED
import dvachmovie.storage.KeyValueStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

open class SettingsUseCase @Inject constructor(
        private val pref: KeyValueStorage
) {
    private val LOADING_PARAM = "LoadingParam"

    fun getLoadingParam(): Boolean {
        if (pref.getBoolean(LOADING_PARAM) != null) {
            return pref.getBoolean(LOADING_PARAM)!!
        }
        return LOADING_NOT_NEEDED
    }

    fun putLoadingParam(value: Boolean) {
        val job = SupervisorJob()
        val scope = CoroutineScope(Dispatchers.Default + job)

        scope.launch {
            pref.putBoolean(LOADING_PARAM, value)
        }
    }
}