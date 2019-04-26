package dvachmovie.api.model.thread

import com.google.gson.annotations.SerializedName

data class FileItem(@SerializedName("tn_height")
                    val tnHeight: Int = 0,
                    @SerializedName("thumbnail")
                    val thumbnail: String = "",
                    @SerializedName("sticker")
                    val sticker: String = "",
                    @SerializedName("type")
                    val type: Long = 0,
                    @SerializedName("pack")
                    val pack: String = "",
                    @SerializedName("path")
                    val path: String = "",
                    @SerializedName("tn_width")
                    val tnWidth: Int = 0,
                    @SerializedName("size")
                    val size: Int = 0,
                    @SerializedName("install")
                    val install: String = "",
                    @SerializedName("displayname")
                    val displayname: String = "",
                    @SerializedName("name")
                    val name: String = "",
                    @SerializedName("width")
                    val width: Int = 0,
                    @SerializedName("height")
                    val height: Int = 0,
                    @SerializedName("fullname")
                    val fullname: String = "",
                    @SerializedName("md5")
                    val md5: String = "",
                    @SerializedName("nsfw")
                    val nsfw: Int = 0,
                    val date: String = "",
                    val thread: Long = 0,
                    val num: Long = 0)
