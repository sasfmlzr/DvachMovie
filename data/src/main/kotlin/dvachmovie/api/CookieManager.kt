package dvachmovie.api

import okhttp3.CookieJar

interface CookieManager {
    fun getCookieJar() : CookieJar
    fun getCookie() : Cookie
}
