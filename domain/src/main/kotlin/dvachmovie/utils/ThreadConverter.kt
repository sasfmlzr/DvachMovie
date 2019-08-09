package dvachmovie.utils

import dvachmovie.api.FileItem
import dvachmovie.db.data.Thread

interface ThreadConverter {
    fun convertFileItemToThread(fileItems: List<FileItem>, baseUrl: String): List<Thread>
}
