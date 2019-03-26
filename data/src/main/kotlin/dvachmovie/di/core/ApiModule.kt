package dvachmovie.di.core

import dagger.Module
import dagger.Provides
import dvachmovie.api.ContactsApi
import dvachmovie.api.CookieManager
import dvachmovie.api.DvachMovieApi
import dvachmovie.api.getOwnerContactConverterFactory
import dvachmovie.data.BuildConfig
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
internal class ApiModule {
    companion object {
        private const val port = 8000
    }

    @Provides
    @Singleton
    fun dvachRetrofitService(cookieJar: CookieJar): DvachMovieApi {
        val httpClient = OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .build()
        val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.DVACH_URL)
                .client(httpClient)
                .addConverterFactory(getOwnerContactConverterFactory())
                .build()
        return retrofit.create(DvachMovieApi::class.java)
    }

    @Provides
    @Singleton
    fun cookieJar(cookieManager: CookieManager) =
            cookieManager.getCookieJar()

    @Provides
    @Singleton
    fun contactsRetrofitService(): ContactsApi {
        val httpUrl = HttpUrl.Builder()
                .host(BuildConfig.CONTACT_URL)
                .port(port)
                .scheme("http")
                .build()
        val retrofit = Retrofit.Builder()
                .baseUrl(httpUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return retrofit.create(ContactsApi::class.java)
    }
}
