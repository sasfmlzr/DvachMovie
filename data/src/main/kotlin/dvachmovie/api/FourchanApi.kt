package dvachmovie.api

import dvachmovie.api.model.gson.b4chan.catalog.FourChanCatalogDTO
import dvachmovie.api.model.gson.b4chan.thread.FourChanThreadDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface FourchanApi {
    @GET("/{board}/catalog.json")
    suspend fun getCatalog(@Path("board") board: String): List<FourChanCatalogDTO>

    @GET("/{board}/thread/{threadNumber}.json")
    suspend fun getThread(@Path("board") board: String,
                          @Path("threadNumber") threadNumber: String): FourChanThreadDTO
}
