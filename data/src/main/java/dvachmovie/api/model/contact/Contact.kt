package dvachmovie.api.model.contact

import com.google.gson.annotations.SerializedName

data class Contact (@SerializedName("id") val id: String ="",
                    @SerializedName("contacts") val contacts: HashMap<String, String> = hashMapOf())