package dvachmovie.api.model.gson.dvach.newthread

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Generated("com.robohorse.robopojogenerator")
data class DvachGetThreadRequestDTO(

	@field:SerializedName("date")
	val date: String = "",

	@field:SerializedName("op")
	val op: Int = 0,

	@field:SerializedName("parent")
	val parent: String = "",

	@field:SerializedName("subject")
	val subject: String = "",

	@field:SerializedName("num")
	val num: String = "",

	@field:SerializedName("endless")
	val endless: Int = 0,

	@field:SerializedName("lasthit")
	val lasthit: Int = 0,

	@field:SerializedName("tags")
	val tags: String = "",

	@field:SerializedName("unique_posters")
	val uniquePosters: String = "",

	@field:SerializedName("trip_type")
	val tripType: String = "",

	@field:SerializedName("trip")
	val trip: String = "",

	@field:SerializedName("name")
	val name: String = "",

	@field:SerializedName("sticky")
	val sticky: Int = 0,

	@field:SerializedName("closed")
	val closed: Int = 0,

	@field:SerializedName("files")
	val files: List<FilesDTO> = listOf(),

	@field:SerializedName("comment")
	val comment: String = "",

	@field:SerializedName("banned")
	val banned: Int = 0,

	@field:SerializedName("email")
	val email: String = "",

	@field:SerializedName("timestamp")
	val timestamp: Int = 0
)
