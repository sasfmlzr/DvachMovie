package dvachmovie.request.model.catalog

import com.google.gson.annotations.SerializedName

data class DvachCatalogRequest(@SerializedName("enable_names")
                               val enableNames: Int = 0,
                               @SerializedName("advert_top_link")
                               val advertTopLink: String = "",
                               @SerializedName("BoardName")
                               val boardName: String = "",
                               @SerializedName("BoardInfoOuter")
                               val boardInfoOuter: String = "",
                               @SerializedName("board_banner_link")
                               val boardBannerLink: String = "",
                               @SerializedName("max_files_size")
                               val maxFilesSize: Int = 0,
                               @SerializedName("advert_bottom_link")
                               val advertBottomLink: String = "",
                               @SerializedName("board_banner_image")
                               val boardBannerImage: String = "",
                               @SerializedName("bump_limit")
                               val bumpLimit: Int = 0,
                               @SerializedName("Board")
                               val board: String = "",
                               @SerializedName("top")
                               val top: List<TopItem>?,
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
                               @SerializedName("news_abu")
                               val newsAbu: List<NewsAbuItem>?,
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
                               val threads: List<ThreadsItem>?,
                               @SerializedName("enable_subject")
                               val enableSubject: Int = 0,
                               @SerializedName("filter")
                               val filter: String = "",
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
                               @SerializedName("advert_bottom_image")
                               val advertBottomImage: String = "",
                               @SerializedName("enable_images")
                               val enableImages: Int = 0)