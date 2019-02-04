package dvachmovie.api.model.catalog

import com.google.gson.annotations.SerializedName

data class NewsAbuItem(@SerializedName("date")
                       val date: String = "",
                       @SerializedName("subject")
                       val subject: String = "",
                       @SerializedName("num")
                       val num: Int = 0,
                       @SerializedName("views")
                       val views: Int = 0)
