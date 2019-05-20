package dvachmovie.api

import dvachmovie.storage.SettingsStorage
import kotlinx.coroutines.runBlocking
import okhttp3.CookieJar
import okhttp3.HttpUrl
import javax.inject.Inject

internal class LocalCookieManager @Inject constructor(
        private val settingsStorage: SettingsStorage) : CookieManager {

    companion object {
        private const val header = "usercode_auth"
    }

    override fun getCookieJar() = cookieJar

    override fun getCookie() = cookie

    private lateinit var cookieJar: CookieJar

    private lateinit var cookieValue: String

    private lateinit var cookie: Cookie

    init {
        runBlocking {
            cookieValue = settingsStorage.getCookie().await()
            cookie = Cookie(header, cookieValue)

            cookieJar = object : CookieJar {
                override fun saveFromResponse(url: HttpUrl, cookies: MutableList<okhttp3.Cookie>) {
                }

                override fun loadForRequest(url: HttpUrl): MutableList<okhttp3.Cookie> {
                    cookie = Cookie(header, cookieValue)
                    return mutableListOf(okhttp3.Cookie
                            .Builder()
                            .name(header)
                            .value(cookieValue)
                            .domain("2ch.hk")
                            .build())
                }
            }
        }
    }
}
