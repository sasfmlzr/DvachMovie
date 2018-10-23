package dvachmovie.request.model.catalog

import com.google.gson.annotations.SerializedName

data class ThreadsItem(@SerializedName("date")
                       val date: String = "",
                       @SerializedName("op")
                       val op: Int = 0,
                       @SerializedName("parent")
                       val parent: String = "",
                       @SerializedName("files_count")
                       val filesCount: Int = 0,
                       @SerializedName("subject")
                       val subject: String = "",
                       @SerializedName("num")
                       val num: String = "",
                       @SerializedName("endless")
                       val endless: Int = 0,
                       @SerializedName("lasthit")
                       val lasthit: Int = 0,
                       @SerializedName("tags")
                       val tags: String = "",
                       @SerializedName("trip")
                       val trip: String = "",
                       @SerializedName("name")
                       val name: String = "",
                       @SerializedName("sticky")
                       val sticky: Int = 0,
                       @SerializedName("closed")
                       val closed: Int = 0,
                       @SerializedName("files")
                       val files: List<FilesItem>?,
                       @SerializedName("comment")
                       val comment: String = "",
                       @SerializedName("banned")
                       val banned: Int = 0,
                       @SerializedName("email")
                       val email: String = "",
                       @SerializedName("posts_count")
                       val postsCount: Int = 0,
                       @SerializedName("timestamp")
                       val timestamp: Int = 0)