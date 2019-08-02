package dvachmovie.utils

import dvachmovie.api.FileItem
import dvachmovie.db.data.Movie

interface MovieConverter {
    fun convertFileItemToMovie(fileItems: List<FileItem>, board: String, baseUrl: String): List<Movie>
}
