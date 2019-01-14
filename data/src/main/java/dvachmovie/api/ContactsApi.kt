package dvachmovie.api

import dvachmovie.Constraints.Companion.CONTACT_URL
import dvachmovie.api.model.contact.OwnerContacts
import retrofit2.Call
import retrofit2.http.*

interface ContactsApi {
    @GET("contacts/get")
    fun getAllOwnerContacts(): Call<List<OwnerContacts>>

    @GET("contacts/get/{id}")
    fun getOwnerById(@Path("id") id: String): Call<OwnerContacts>

    @POST("contacts/new")
    fun putContacts(@Body contact: OwnerContacts): Call<OwnerContacts>

    @PUT("contacts/put/{id}")
    fun putNewContacts(@Path("id") id: String, @Body contact: OwnerContacts): Call<OwnerContacts>
}