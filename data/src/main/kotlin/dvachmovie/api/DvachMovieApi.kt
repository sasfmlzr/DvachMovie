package dvachmovie.api

import dvachmovie.api.model.DvachReportRequest
import dvachmovie.api.model.catalog.DvachCatalogRequest
import dvachmovie.api.model.thread.DvachThreadRequest
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DvachMovieApi {
    @GET("/{board}/catalog.json")
    fun getCatalog(@Path("board") board: String): Call<DvachCatalogRequest>

    @GET("/{board}/res/{numThread}.json")
    fun getThread(@Path("board") board: String,
                  @Path("numThread") numThread: String): Call<DvachThreadRequest>

    @POST("/makaba/makaba.fcgi")
    @FormUrlEncoded
    fun reportPost(@Field("task") task: String,
                   @Field("board") board: String,
                   @Field("thread") thread: Long,
                   @Field("comment") comment: String,
                   @Field("post") post: Long,
                   @Field("json") json: String = "1") : Call<DvachReportRequest>
}
