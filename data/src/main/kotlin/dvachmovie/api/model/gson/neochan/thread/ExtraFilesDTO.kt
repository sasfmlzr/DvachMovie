package dvachmovie.api.model.gson.neochan.thread

import com.google.gson.annotations.SerializedName

data class ExtraFilesDTO(

        @field:SerializedName("tn_h")
        val tnH: Int = 0,

        @field:SerializedName("tn_w")
        val tnW: Int = 0,

        @field:SerializedName("h")
        val H: Int = 0,

        @field:SerializedName("w")
        val W: Int = 0,

        @field:SerializedName("fsize")
        val fsize: Int = 0,

        @field:SerializedName("filename")
        val filename: String = "",

        @field:SerializedName("ext")
        val ext: String = "",

        @field:SerializedName("tim")
        val tim: String = "",

        @field:SerializedName("spoiler")
        val spoiler: Int = 0,

        @field:SerializedName("md5")
        val md5: String = ""
)
