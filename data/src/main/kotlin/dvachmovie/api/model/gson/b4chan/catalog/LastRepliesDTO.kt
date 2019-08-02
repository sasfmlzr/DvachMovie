package dvachmovie.api.model.gson.b4chan.catalog

import com.google.gson.annotations.SerializedName

data class LastRepliesDTO(

        @field:SerializedName("com")
        val com: String = "",

        @field:SerializedName("ext")
        val ext: String = "",

        @field:SerializedName("no")
        val no: Int = 0,

        @field:SerializedName("tn_w")
        val tnW: Int = 0,

        @field:SerializedName("h")
        val H: Int = 0,

        @field:SerializedName("resto")
        val resto: Int = 0,

        @field:SerializedName("filename")
        val filename: String = "",

        @field:SerializedName("fsize")
        val fsize: Int = 0,

        @field:SerializedName("tn_h")
        val tnH: Int = 0,

        @field:SerializedName("now")
        val now: String = "",

        @field:SerializedName("w")
        val W: Int = 0,

        @field:SerializedName("name")
        val name: String = "",

        @field:SerializedName("tim")
        val tim: Long = 0,

        @field:SerializedName("time")
        val time: Int = 0,

        @field:SerializedName("m_img")
        val mImg: Int = 0,

        @field:SerializedName("md5")
        val md5: String = ""
)