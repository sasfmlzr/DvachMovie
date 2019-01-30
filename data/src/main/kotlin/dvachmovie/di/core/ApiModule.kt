package dvachmovie.di.core

import dagger.Module
import dagger.Provides
import dvachmovie.api.ContactsApi
import dvachmovie.api.DvachMovieApi
import dvachmovie.data.BuildConfig
import okhttp3.HttpUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApiModule {
    companion object {
        private const val port = 8000
    }

    @Provides
    @Singleton
    fun dvachRetrofitService(): DvachMovieApi {
        val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.DVACH_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return retrofit.create(DvachMovieApi::class.java)
    }

    @Provides
    @Singleton
    fun getContactsRetrofitService(): ContactsApi {
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
