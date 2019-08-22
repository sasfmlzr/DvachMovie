package dvachmovie.api

import dvachmovie.api.model.gson.neochan.catalog.NeoChanCatalogDTO
import dvachmovie.api.model.gson.neochan.thread.NeoChanThreadDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface NeoChanApi {
    @GET("/{board}/catalog.json")
    suspend fun getCatalog(@Path("board") board: String): List<NeoChanCatalogDTO>

    @GET("/{board}/res/{numThread}.json")
    suspend fun getThread(@Path("board") board: String,
                          @Path("numThread") numThread: String): NeoChanThreadDTO
}
