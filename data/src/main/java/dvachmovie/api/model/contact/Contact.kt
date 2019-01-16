package dvachmovie.api.model.contact

import com.google.gson.annotations.SerializedName

data class Contact(@SerializedName("name") val name: String = "",
                   @SerializedName("phone") val phone: String = "")