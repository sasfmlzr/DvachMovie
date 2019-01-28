package dvachmovie.di.core

import dagger.Module
import dagger.Provides
import dvachmovie.CONTACT_URL
import dvachmovie.DVACH_URL
import dvachmovie.api.ContactsApi
import dvachmovie.api.DvachMovieApi
import okhttp3.HttpUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApiModule {
    @Provides
    @Singleton
    fun dvachRetrofitService(): DvachMovieApi {
        val retrofit = Retrofit.Builder()
                .baseUrl(DVACH_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return retrofit.create(DvachMovieApi::class.java)
    }

    @Provides
    @Singleton
    fun getContactsRetrofitService(): ContactsApi {
        val port = 8000
        val httpUrl = HttpUrl.Builder()
                .host(CONTACT_URL)
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
