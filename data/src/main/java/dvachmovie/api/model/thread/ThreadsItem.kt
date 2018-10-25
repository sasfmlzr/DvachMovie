package dvachmovie.api.model.thread

import com.google.gson.annotations.SerializedName

data class ThreadsItem(@SerializedName("posts")
                       val posts: List<PostsItem>?)