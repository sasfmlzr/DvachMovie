package dvachmovie.api

import dvachmovie.api.model.contact.UniqueContactName
import dvachmovie.api.model.contact.OwnerContacts
import dvachmovie.api.model.location.PutLocation
import retrofit2.Call
import retrofit2.http.*

interface ContactsApi {
    @PUT("contacts/put/{id}")
    fun putNewContacts(@Path("id") id: String, @Body contact: OwnerContacts): Call<OwnerContacts>

    @PUT("contacts/putlocation/{id}")
    fun putLocationInContact(@Path("id") id: String, @Body location: PutLocation): Call<OwnerContacts>

    @GET("contacts/checkid/{id}")
    fun checkUniqueContacts(@Path("id") id: String): Call<UniqueContactName>
}
