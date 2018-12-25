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

    fun getLoadingParam(): Int {
        if (pref.getInt(LOADING_PARAM) != null) {
            return pref.getInt(LOADING_PARAM)!!
        }
        return LOADING_NOT_NEEDED
    }

    fun putLoadingParam(value: Int) {
        val job = SupervisorJob()
        val scope = CoroutineScope(Dispatchers.Default + job)

        scope.launch {
            pref.putInt(LOADING_PARAM, value)
        }
    }
}