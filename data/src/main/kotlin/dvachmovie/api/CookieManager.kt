package dvachmovie.api

import dvachmovie.storage.SettingsStorage
import okhttp3.CookieJar
import okhttp3.HttpUrl
import javax.inject.Inject

class CookieManager @Inject constructor(private val settingsStorage: SettingsStorage) {

    private val header = "usercode_auth"

    val cookieJar = object : CookieJar {
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

    var cookie = Cookie(header, settingsStorage.getCookie())

    data class Cookie(val header: String,
                      val value: String) {

        override fun toString(): String {
            return "$header=$value"
        }
    }
}
