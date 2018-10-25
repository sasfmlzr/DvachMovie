package dvachmovie.api.model.catalog

import com.google.gson.annotations.SerializedName

data class TopItem(@SerializedName("name")
                   val name: String = "",
                   @SerializedName("board")
                   val board: String = "",
                   @SerializedName("info")
                   val info: String = "")