package dvachmovie.api.model.gson

import com.google.gson.Gson
import dvachmovie.api.model.gson.b4chan.catalog.FourChanCatalogDTO
import dvachmovie.api.model.gson.b4chan.catalog.LastRepliesDTO
import dvachmovie.api.model.gson.b4chan.catalog.ThreadsDTO
import dvachmovie.api.model.gson.b4chan.thread.FourChanThreadDTO
import dvachmovie.api.model.gson.b4chan.thread.PostsDTO
import dvachmovie.api.model.gson.dvach.DvachReportRequest
import dvachmovie.api.model.gson.dvach.catalog.*
import dvachmovie.api.model.gson.dvach.thread.DvachThreadRequest
import dvachmovie.api.model.gson.dvach.thread.FileItem
import dvachmovie.api.model.gson.dvach.thread.PostsItem
import dvachmovie.api.model.gson.neochan.catalog.ExtraFilesDTO
import dvachmovie.api.model.gson.neochan.catalog.NeoChanCatalogDTO
import dvachmovie.api.model.gson.neochan.thread.NeoChanThreadDTO
import org.junit.Test

class GsonExperiment {

    @Test
    fun fourChanHappyPassCreateJson() {
        Gson().toJson(FourChanCatalogDTO(threads = listOf(ThreadsDTO(
                lastReplies = listOf(LastRepliesDTO())))))
        Gson().toJson(FourChanThreadDTO(posts = listOf(PostsDTO())))
    }

    @Test
    fun dvachHappyPassCreateJson() {
        Gson().toJson(DvachCatalogRequest(top = listOf(TopItem()),
                threads = listOf(ThreadsItem(files = listOf(FilesItem()))),
                newsAbu = listOf(NewsAbuItem())))
        Gson().toJson(DvachThreadRequest(top = listOf(TopItem()),
                newsAbu = listOf(dvachmovie.api.model.gson.dvach.thread.NewsAbuItem()),
                threads = listOf(dvachmovie.api.model.gson.dvach.thread.ThreadsItem(
                        posts = listOf(PostsItem(files = listOf(FileItem())))))))
    }

    @Test
    fun neoChanHappyPassCreateJson() {
        Gson().toJson(NeoChanCatalogDTO(
                threads = listOf(dvachmovie.api.model.gson.neochan.catalog.ThreadsDTO(
                        extraFiles = listOf(ExtraFilesDTO())))))
        Gson().toJson(NeoChanThreadDTO(
                posts = listOf(dvachmovie.api.model.gson.neochan.thread.PostsDTO(
                        extraFiles = listOf(ExtraFilesDTO())))))
        Gson().toJson(DvachReportRequest())
    }
}
