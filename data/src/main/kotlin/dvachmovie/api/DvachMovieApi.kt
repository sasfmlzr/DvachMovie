package dvachmovie.api

import dvachmovie.api.model.gson.dvach.DvachReportRequest
import dvachmovie.api.model.gson.dvach.catalog.DvachCatalogRequest
import dvachmovie.api.model.gson.dvach.thread.DvachThreadRequest
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DvachMovieApi {
    @GET("/{board}/catalog.json")
    suspend fun getCatalog(@Path("board") board: String): DvachCatalogRequest

    @GET("/{board}/res/{numThread}.json")
    suspend fun getThread(@Path("board") board: String,
                          @Path("numThread") numThread: String): DvachThreadRequest

    @POST("/makaba/makaba.fcgi")
    @FormUrlEncoded
    suspend fun reportPost(@Field("task") task: String,
                           @Field("board") board: String,
                           @Field("thread") thread: Long,
                           @Field("comment") comment: String,
                           @Field("post") post: Long,
                           @Field("json") json: String = "1"): DvachReportRequest
}
