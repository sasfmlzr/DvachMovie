package dvachmovie.storage

import dvachmovie.api.Cookie
import dvachmovie.api.CookieStorage
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class LocalCookieStorage @Inject constructor(
        private val settingsStorage: SettingsStorage) : CookieStorage {

    companion object {
        private const val header = "usercode_auth"
    }

    override val cookie : Cookie by lazy {
        runBlocking {
            Cookie(header,  settingsStorage.getCookie().await())
        }
    }
}
