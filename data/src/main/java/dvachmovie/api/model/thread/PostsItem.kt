package dvachmovie.api.model.thread

import com.google.gson.annotations.SerializedName

data class PostsItem(@SerializedName("date")
                     val date: String = "",
                     @SerializedName("op")
                     val op: Int = 0,
                     @SerializedName("parent")
                     val parent: String = "",
                     @SerializedName("subject")
                     val subject: String = "",
                     @SerializedName("num")
                     val num: Int = 0,
                     @SerializedName("endless")
                     val endless: Int = 0,
                     @SerializedName("lasthit")
                     val lasthit: Int = 0,
                     @SerializedName("tags")
                     val tags: String = "",
                     @SerializedName("number")
                     val number: Int = 0,
                     @SerializedName("trip")
                     val trip: String = "",
                     @SerializedName("name")
                     val name: String = "",
                     @SerializedName("sticky")
                     val sticky: Int = 0,
                     @SerializedName("closed")
                     val closed: Int = 0,
                     @SerializedName("files")
                     val files: List<FileItem>?,
                     @SerializedName("comment")
                     val comment: String = "",
                     @SerializedName("banned")
                     val banned: Int = 0,
                     @SerializedName("email")
                     val email: String = "",
                     @SerializedName("timestamp")
                     val timestamp: Int = 0)