package dvachmovie.api

import dvachmovie.storage.SettingsStorage
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

internal class LocalCookieManager @Inject constructor(
        private val settingsStorage: SettingsStorage) : CookieManager {

    companion object {
        private const val header = "usercode_auth"
    }

    override fun getCookie() = cookie

    private lateinit var cookieValue: String

    private lateinit var cookie: Cookie

    init {
        runBlocking {
            cookieValue = settingsStorage.getCookie().await()
            cookie = Cookie(header, cookieValue)
        }
    }
}
