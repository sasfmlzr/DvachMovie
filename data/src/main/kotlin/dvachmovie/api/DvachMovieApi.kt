package dvachmovie.api

import dvachmovie.api.model.DvachReportRequest
import dvachmovie.api.model.gson.dvach.catalog.DvachCatalogRequest
import dvachmovie.api.model.gson.dvach.newthread.DvachGetThreadRequestDTO
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DvachMovieApi {
    @GET("/{board}/catalog.json")
    suspend fun getCatalog(@Path("board") board: String): DvachCatalogRequest

    @POST("/makaba/mobile.fcgi")
    @FormUrlEncoded
    suspend fun getThreadNew(@Field("board") board: String,
                             @Field("thread") thread: String,
                             @Field("num") num: String,
                             @Field("task") task: String? = "get_thread"): List<DvachGetThreadRequestDTO>

    @POST("/makaba/makaba.fcgi")
    @FormUrlEncoded
    suspend fun reportPost(@Field("task") task: String,
                           @Field("board") board: String,
                           @Field("thread") thread: Long,
                           @Field("comment") comment: String,
                           @Field("post") post: Long,
                           @Field("json") json: String = "1"): DvachReportRequest
}
