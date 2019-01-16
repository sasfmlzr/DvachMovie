package dvachmovie.api

import dvachmovie.Constants.Companion.BASE_URL
import okhttp3.HttpUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitSingleton {
    private var dvachMovieApi: DvachMovieApi? = null

    fun getDvachMovieApi(): DvachMovieApi? {
        if (dvachMovieApi == null) {
            val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            dvachMovieApi = retrofit.create(DvachMovieApi::class.java)
        }
        return dvachMovieApi
    }

    private var contactsApi: ContactsApi? = null

    fun getContactsApi(): ContactsApi? {
        val httpUrl = HttpUrl.Builder()
                .host("192.168.1.112")
                .port(8000)
                .scheme("http")
                .build()
        if (contactsApi == null) {
            val retrofit = Retrofit.Builder()
                   .baseUrl(httpUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            contactsApi = retrofit.create(ContactsApi::class.java)
        }
        return contactsApi
    }
}