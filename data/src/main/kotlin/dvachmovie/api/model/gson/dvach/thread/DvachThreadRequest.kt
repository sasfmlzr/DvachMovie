package dvachmovie.api.model.gson.dvach.thread

import com.google.gson.annotations.SerializedName
import dvachmovie.api.model.gson.dvach.catalog.TopItem

data class DvachThreadRequest(@SerializedName("enable_names")
                              val enableNames: Int = 0,
                              @SerializedName("advert_top_link")
                              val advertTopLink: String = "",
                              @SerializedName("BoardName")
                              val boardName: String = "",
                              @SerializedName("current_thread")
                              val currentThread: String = "",
                              @SerializedName("BoardInfoOuter")
                              val boardInfoOuter: String = "",
                              @SerializedName("board_banner_link")
                              val boardBannerLink: String = "",
                              @SerializedName("max_files_size")
                              val maxFilesSize: Int = 0,
                              @SerializedName("title")
                              val title: String = "",
                              @SerializedName("advert_bottom_link")
                              val advertBottomLink: String = "",
                              @SerializedName("board_banner_image")
                              val boardBannerImage: String = "",
                              @SerializedName("bump_limit")
                              val bumpLimit: Int = 0,
                              @SerializedName("unique_posters")
                              val uniquePosters: String = "",
                              @SerializedName("Board")
                              val board: String = "",
                              @SerializedName("top")
                              val top: List<TopItem> = listOf(),
                              @SerializedName("advert_mobile_image")
                              val advertMobileImage: String = "",
                              @SerializedName("enable_icons")
                              val enableIcons: Int = 0,
                              @SerializedName("enable_trips")
                              val enableTrips: Int = 0,
                              @SerializedName("enable_oekaki")
                              val enableOekaki: Int = 0,
                              @SerializedName("advert_top_image")
                              val advertTopImage: String = "",
                              @SerializedName("is_closed")
                              val isClosed: Int = 0,
                              @SerializedName("news_abu")
                              val newsAbu: List<NewsAbuItem> = listOf(),
                              @SerializedName("is_index")
                              val isIndex: Int = 0,
                              @SerializedName("files_count")
                              val filesCount: Int = 0,
                              @SerializedName("BoardInfo")
                              val boardInfo: String = "",
                              @SerializedName("enable_posting")
                              val enablePosting: Int = 0,
                              @SerializedName("enable_thread_tags")
                              val enableThreadTags: Int = 0,
                              @SerializedName("advert_mobile_link")
                              val advertMobileLink: String = "",
                              @SerializedName("enable_flags")
                              val enableFlags: Int = 0,
                              @SerializedName("threads")
                              val threads: List<ThreadsItem> = listOf(),
                              @SerializedName("is_board")
                              val isBoard: Int = 0,
                              @SerializedName("enable_subject")
                              val enableSubject: Int = 0,
                              @SerializedName("default_name")
                              val defaultName: String = "",
                              @SerializedName("enable_dices")
                              val enableDices: Int = 0,
                              @SerializedName("enable_likes")
                              val enableLikes: Int = 0,
                              @SerializedName("max_comment")
                              val maxComment: Int = 0,
                              @SerializedName("enable_shield")
                              val enableShield: Int = 0,
                              @SerializedName("enable_sage")
                              val enableSage: Int = 0,
                              @SerializedName("enable_video")
                              val enableVideo: Int = 0,
                              @SerializedName("max_num")
                              val maxNum: Int = 0,
                              @SerializedName("advert_bottom_image")
                              val advertBottomImage: String = "",
                              @SerializedName("enable_images")
                              val enableImages: Int = 0,
                              @SerializedName("posts_count")
                              val postsCount: Int = 0)
