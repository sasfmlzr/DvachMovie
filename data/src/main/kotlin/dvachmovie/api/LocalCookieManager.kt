package dvachmovie.api

import dvachmovie.storage.SettingsStorage
import okhttp3.CookieJar
import okhttp3.HttpUrl
import javax.inject.Inject

class LocalCookieManager @Inject constructor(
        private val settingsStorage: SettingsStorage) : CookieManager {

    companion object {
        private const val header = "usercode_auth"
    }

    override fun getCookieJar() = cookieJar

    override fun getCookie() = cookie

    private val cookieJar = object : CookieJar {
        override fun saveFromResponse(url: HttpUrl, cookies: MutableList<okhttp3.Cookie>) {
        }

        override fun loadForRequest(url: HttpUrl): MutableList<okhttp3.Cookie> {
            val value = settingsStorage.getCookie()
            cookie = Cookie(header, value)
            return mutableListOf(okhttp3.Cookie
                    .Builder()
                    .name(header)
                    .value(value)
                    .domain("2ch.hk")
                    .build())
        }
    }

    private var cookie = Cookie(header, settingsStorage.getCookie())
}
