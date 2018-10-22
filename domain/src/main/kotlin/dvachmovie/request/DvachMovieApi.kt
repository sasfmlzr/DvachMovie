package dvachmovie.request

import dvachmovie.request.model.DvachCatalogRequest
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DvachMovieApi {
    @GET("https://2ch.hk/{board}/catalog.json")
    fun getCatalog(@Path("board") board: String): Call<DvachCatalogRequest>
}