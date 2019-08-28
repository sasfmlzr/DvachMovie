package dvachmovie.api.model.gson.b4chan.thread

import com.google.gson.annotations.SerializedName

data class FourChanThreadDTO(

        @field:SerializedName("posts")
        val posts: List<PostsDTO> = listOf()
)
