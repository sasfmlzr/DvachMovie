package dvachmovie.di.core

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dvachmovie.api.CookieManager
import dvachmovie.api.DvachMovieApi
import dvachmovie.api.getOwnerContactConverterFactory
import dvachmovie.data.BuildConfig
import dvachmovie.storage.local.LocalProxyStorage
import okhttp3.CookieJar
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
internal class ApiModule {

    @Provides
    @Singleton
    fun dvachRetrofitService(cookieJar: CookieJar, proxyStorage: LocalProxyStorage): DvachMovieApi {
        val proxy = proxyStorage.getProxy()
        val httpClient = if (proxy != null) {
            OkHttpClient.Builder()
                    .proxy(proxy)
                    .cookieJar(cookieJar)
                    .build()
        } else {
            OkHttpClient.Builder()
                    .cookieJar(cookieJar)
                    .build()
        }

        val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.DVACH_URL)
                .client(httpClient)
                .addConverterFactory(getOwnerContactConverterFactory())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
        return retrofit.create(DvachMovieApi::class.java)
    }

    @Provides
    @Singleton
    fun cookieJar(cookieManager: CookieManager) =
            cookieManager.getCookieJar()
}
