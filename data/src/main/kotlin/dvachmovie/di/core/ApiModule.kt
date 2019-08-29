package dvachmovie.di.core

import dagger.Module
import dagger.Provides
import dvachmovie.AppConfig
import dvachmovie.api.DvachMovieApi
import dvachmovie.api.FourchanApi
import dvachmovie.api.NeoChanApi
import dvachmovie.api.getOwnerContactConverterFactory
import dvachmovie.storage.SettingsStorage
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
internal class ApiModule {

    @Provides
    @Singleton
    fun dvachRetrofitService(okHttpClient: OkHttpClient): DvachMovieApi {
        val retrofit = Retrofit.Builder()
                .baseUrl(AppConfig.DVACH_URL)
                .client(okHttpClient)
                .addConverterFactory(getOwnerContactConverterFactory())
                .build()
        return retrofit.create(DvachMovieApi::class.java)
    }

    @Provides
    @Singleton
    fun fourChanRetrofitService(okHttpClient: OkHttpClient): FourchanApi {
        val retrofit = Retrofit.Builder()
                .baseUrl(AppConfig.FOURCHAN_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return retrofit.create(FourchanApi::class.java)
    }

    @Provides
    @Singleton
    fun neoChanRetrofitService(okHttpClient: OkHttpClient): NeoChanApi {
        val retrofit = Retrofit.Builder()
                .baseUrl(AppConfig.NEOCHAN_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return retrofit.create(NeoChanApi::class.java)
    }

    @Provides
    @Singleton
    fun okHttp(cookieJar: CookieJar) = OkHttpClient.Builder()
            .connectTimeout(3, TimeUnit.SECONDS)
            .readTimeout(3, TimeUnit.SECONDS)
            .cookieJar(cookieJar)
            .build()

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
