package dvachmovie.api

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import dvachmovie.api.model.gson.dvach.catalog.DvachCatalogRequest
import retrofit2.converter.gson.GsonConverterFactory

internal fun getOwnerContactConverterFactory(): GsonConverterFactory {
    val ownerContactsParser = GsonBuilder()
            .registerTypeAdapter(DvachCatalogRequest::class.java,
                    JsonDeserializer<DvachCatalogRequest> { json, _, _ ->
                        val jComment = json.asJsonObject
                        GsonBuilder().create().fromJson(jComment, DvachCatalogRequest::class.java)
                    })
            .create()
    return GsonConverterFactory.create(ownerContactsParser)
}
