package dvachmovie.api

import dvachmovie.storage.SettingsStorage
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import javax.inject.Inject

class CookieManager @Inject constructor(private val settingsStorage: SettingsStorage){

    private val header = "usercode_auth"

    val cookieJar = object : CookieJar {
        override fun saveFromResponse(url: HttpUrl, cookies: MutableList<Cookie>) {
        }

        override fun loadForRequest(url: HttpUrl): MutableList<Cookie> {
            val value = settingsStorage.getCookie()
            return mutableListOf(Cookie
                    .Builder()
                    .name(header)
                    .value(value)
                    .domain("2ch.hk")
                    .build())
        }
    }

    /**
     * return cookie
     */
    fun getCookie(): String {
        return "$header=${settingsStorage.getCookie()}"
    }

}