package dvachmovie.utils

import dvachmovie.api.FileItem
import dvachmovie.db.data.Movie
import dvachmovie.db.model.MovieEntity
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import javax.inject.Inject

class LocalMovieConverter @Inject constructor() : MovieConverter {
    override fun convertFileItemToMovie(fileItems: List<FileItem>, board: String, baseUrl: String): List<Movie> =
            fileItems.map { fileItem ->
                MovieEntity(board = board,
                        movieUrl = baseUrl + fileItem.path,
                        previewUrl = baseUrl + fileItem.thumbnail,
                        date = parseDateFromFileItem(fileItem),
                        md5 = fileItem.md5,
                        thread = fileItem.numThread,
                        post = fileItem.numPost,
                        baseUrl = baseUrl)
            }

    private fun parseDateFromFileItem(fileItem: FileItem): LocalDateTime =
            LocalDateTime.parse(fileItem.date,
                    DateTimeFormat.forPattern
                    ("dd/MM/YYYY '${fileItem.date.substring(9, 12)}' HH:mm:ss"))
                    .plusYears(2000)
}
