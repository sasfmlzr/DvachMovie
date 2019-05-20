package dvachmovie.api.model.gson.dvach.catalog

import com.google.gson.annotations.SerializedName

data class FilesItem(@SerializedName("tn_height")
                     val tnHeight: Int = 0,
                     @SerializedName("thumbnail")
                     val thumbnail: String = "",
                     @SerializedName("nsfw")
                     val nsfw: Int = 0,
                     @SerializedName("type")
                     val type: Long = 0,
                     @SerializedName("path")
                     val path: String = "",
                     @SerializedName("tn_width")
                     val tnWidth: Int = 0,
                     @SerializedName("size")
                     val size: Int = 0,
                     @SerializedName("displayname")
                     val displayname: String = "",
                     @SerializedName("name")
                     val name: String = "",
                     @SerializedName("width")
                     val width: Int = 0,
                     @SerializedName("fullname")
                     val fullname: String = "",
                     @SerializedName("height")
                     val height: Int = 0,
                     @SerializedName("md5")
                     val md: String = "")
