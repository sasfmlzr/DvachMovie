package dvachmovie.api

import dvachmovie.Constants.Companion.CONTACT_URL
import okhttp3.HttpUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitSingleton {

    private var contactsApi: ContactsApi? = null

    fun getContactsApi(): ContactsApi? {
        val httpUrl = HttpUrl.Builder()
                .host(CONTACT_URL)
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