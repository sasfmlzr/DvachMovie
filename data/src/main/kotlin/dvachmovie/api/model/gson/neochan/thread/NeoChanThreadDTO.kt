package dvachmovie.api.model.gson.neochan.thread

import com.google.gson.annotations.SerializedName

data class NeoChanThreadDTO(

        @field:SerializedName("posts")
        val posts: List<PostsDTO> = listOf()
)