package dvachmovie.api.model.gson.dvach.thread

import com.google.gson.annotations.SerializedName

data class ThreadsItem(@SerializedName("posts")
                       val posts: List<PostsItem>?)
