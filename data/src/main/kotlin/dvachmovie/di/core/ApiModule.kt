package dvachmovie.di.core

import dagger.Module
import dagger.Provides
import dvachmovie.AppConfig
import dvachmovie.api.DvachMovieApi
import dvachmovie.api.getOwnerContactConverterFactory
import dvachmovie.storage.SettingsStorage
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
internal class ApiModule {

    @Provides
    @Singleton
    fun dvachRetrofitService(cookieJar: CookieJar): DvachMovieApi {
        val httpClient = OkHttpClient.Builder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
                .cookieJar(cookieJar)
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl(AppConfig.DVACH_URL)
                .client(httpClient)
                .addConverterFactory(getOwnerContactConverterFactory())
                .build()
        return retrofit.create(DvachMovieApi::class.java)
    }

    @Provides
    @Singleton
    fun cookieJar(settingsStorage: SettingsStorage) = object : CookieJar {
        private val header = "usercode_auth"

        val cookieValue = settingsStorage.getCookie()

        override fun saveFromResponse(url: HttpUrl, cookies: List<okhttp3.Cookie>) {
        }

        override fun loadForRequest(url: HttpUrl): MutableList<okhttp3.Cookie> {
            return mutableListOf(okhttp3.Cookie
                    .Builder()
                    .name(header)
                    .value(cookieValue)
                    .domain("2ch.hk")
                    .build())
        }
    }
}
