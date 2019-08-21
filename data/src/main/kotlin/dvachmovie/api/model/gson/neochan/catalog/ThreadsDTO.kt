package dvachmovie.api.model.gson.neochan.catalog

import com.google.gson.annotations.SerializedName

data class ThreadsDTO(

        @field:SerializedName("com")
        val com: String = "",

        @field:SerializedName("no")
        val no: Int = 0,

        @field:SerializedName("sub")
        val sub: String = "",

        @field:SerializedName("tn_w")
        val tnW: Int = 0,

        @field:SerializedName("trip")
        val trip: String = "",

        @field:SerializedName("omitted_images")
        val omittedImages: Int = 0,

        @field:SerializedName("tn_h")
        val tnH: Int = 0,

        @field:SerializedName("tim")
        val tim: String = "",

        @field:SerializedName("spoiler")
        val spoiler: Int = 0,

        @field:SerializedName("locked")
        val locked: Int = 0,

        @field:SerializedName("cyclical")
        val cyclical: String = "",

        @field:SerializedName("last_modified")
        val lastModified: Int = 0,

        @field:SerializedName("extra_files")
        val extraFiles: List<ExtraFilesDTO> = listOf(),

        @field:SerializedName("ext")
        val ext: String = "",

        @field:SerializedName("images")
        val images: Int = 0,

        @field:SerializedName("h")
        val H: Int = 0,

        @field:SerializedName("resto")
        val resto: Int = 0,

        @field:SerializedName("filename")
        val filename: String = "",

        @field:SerializedName("omitted_posts")
        val omittedPosts: Int = 0,

        @field:SerializedName("replies")
        val replies: Int = 0,

        @field:SerializedName("fsize")
        val fsize: Int = 0,

        @field:SerializedName("w")
        val W: Int = 0,

        @field:SerializedName("sticky")
        val sticky: Int = 0,

        @field:SerializedName("time")
        val time: Int = 0,

        @field:SerializedName("md5")
        val md5: String = ""
)
