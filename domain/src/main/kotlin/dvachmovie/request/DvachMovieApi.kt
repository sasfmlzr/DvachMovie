package dvachmovie.request

import dvachmovie.request.model.catalog.DvachCatalogRequest
import dvachmovie.request.model.thread.DvachThreadRequest
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DvachMovieApi {
    @GET("https://2ch.hk/{board}/catalog.json")
    fun getCatalog(@Path("board") board: String): Call<DvachCatalogRequest>

    @GET("https://2ch.hk/{board}/res/{numThread}.json")
    fun getThread(@Path("board") board: String,
                   @Path("numThread") numThread: String): Call<DvachThreadRequest>
}