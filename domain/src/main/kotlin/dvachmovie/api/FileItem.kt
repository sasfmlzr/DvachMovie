package dvachmovie.api

data class FileItem(
                    val path: String = "",
                    val thumbnail: String = "",
                    val date: String = "",
                    val md5: String = "",
                    val numThread: Long = 0,
                    val numPost: Long = 0)
