package dvachmovie.api.model.gson.b4chan.thread

import com.google.gson.annotations.SerializedName

data class PostsDTO(

        @field:SerializedName("no")
        val no: Int = 0,

        @field:SerializedName("now")
        val now: String = "",

        @field:SerializedName("name")
        val name: String = "",

        @field:SerializedName("com")
        val com: String = "",

        @field:SerializedName("filename")
        val filename: String = "",

        @field:SerializedName("ext")
        val ext: String = "",

        @field:SerializedName("w")
        val W: Int = 0,

        @field:SerializedName("h")
        val H: Int = 0,

        @field:SerializedName("tn_w")
        val tnW: Int = 0,

        @field:SerializedName("tn_h")
        val tnH: Int = 0,

        @field:SerializedName("tim")
        val tim: Long = 0,

        @field:SerializedName("time")
        val time: Int = 0,

        @field:SerializedName("md5")
        val md5: String = "",

        @field:SerializedName("fsize")
        val fsize: Int = 0,

        @field:SerializedName("resto")
        val resto: Int = 0,

        @field:SerializedName("bumplimit")
        val bumplimit: Int = 0,

        @field:SerializedName("imagelimit")
        val imagelimit: Int = 0,

        @field:SerializedName("semantic_url")
        val semanticUrl: String = "",

        @field:SerializedName("replies")
        val replies: Int = 0,

        @field:SerializedName("images")
        val images: Int = 0,

        @field:SerializedName("unique_ips")
        val uniqueIps: Int = 0
)
