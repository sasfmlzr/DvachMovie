package dvachmovie.utils

import dvachmovie.AppConfig
import dvachmovie.api.FileItem
import dvachmovie.db.data.Movie
import dvachmovie.db.model.MovieEntity
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import javax.inject.Inject

class LocalMovieConverter @Inject constructor(private val appConfig: AppConfig) : MovieConverter {
    override fun convertFileItemToMovie(fileItems: List<FileItem>, board: String): List<Movie> =
            fileItems.map { fileItem ->
                MovieEntity(board = board,
                        movieUrl = appConfig.DVACH_URL + fileItem.path,
                        previewUrl = appConfig.DVACH_URL + fileItem.thumbnail,
                        date = parseDateFromFileItem(fileItem),
                        md5 = fileItem.md5,
                        thread = fileItem.numThread,
                        post = fileItem.numPost)
            }

    private fun parseDateFromFileItem(fileItem: FileItem): LocalDateTime =
            LocalDateTime.parse(fileItem.date,
                    DateTimeFormat.forPattern
                    ("dd/MM/YYYY '${fileItem.date.substring(9, 12)}' HH:mm:ss"))
                    .plusYears(2000)
}