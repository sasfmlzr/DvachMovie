package dvachmovie.utils

import dvachmovie.AppConfig
import dvachmovie.api.FileItem
import dvachmovie.db.data.Movie
import dvachmovie.db.model.MovieEntity
import org.joda.time.Instant
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import javax.inject.Inject

class LocalMovieConverter @Inject constructor() : MovieConverter {
    override fun convertFileItemToMovie(fileItems: List<FileItem>,
                                        board: String,
                                        baseUrl: String): List<Movie> =
            fileItems.map { fileItem ->
                when (baseUrl) {
                    AppConfig.DVACH_URL ->
                        MovieEntity(board = board,
                                movieUrl = baseUrl + fileItem.path,
                                previewUrl = baseUrl + fileItem.thumbnail,
                                date = parseDateFromFileItem(fileItem),
                                md5 = fileItem.md5,
                                thread = fileItem.numThread,
                                post = fileItem.numPost,
                                baseUrl = baseUrl)
                    AppConfig.FOURCHAN_URL ->
                        MovieEntity(board = board,
                                movieUrl = fileItem.path,
                                previewUrl = fileItem.thumbnail,
                                date = Instant.ofEpochSecond(fileItem.date.toLong())
                                        .toDateTime()
                                        .toLocalDateTime(),
                                md5 = fileItem.md5,
                                thread = fileItem.numThread,
                                post = fileItem.numPost,
                                baseUrl = baseUrl)
                    AppConfig.NEOCHAN_URL ->
                        MovieEntity(board = board,
                                movieUrl = fileItem.path,
                                previewUrl = fileItem.thumbnail,
                                date = Instant.ofEpochSecond(fileItem.date.toLong())
                                        .toDateTime()
                                        .toLocalDateTime(),
                                md5 = fileItem.md5,
                                thread = fileItem.numThread,
                                post = fileItem.numPost,
                                baseUrl = baseUrl)
                    else -> throw RuntimeException("I don't know such imageboard")
                }
            }

    private fun parseDateFromFileItem(fileItem: FileItem): LocalDateTime =
            LocalDateTime.parse(fileItem.date,
                    DateTimeFormat.forPattern
                    ("dd/MM/YYYY '${fileItem.date.substring(9, 12)}' HH:mm:ss"))
                    .plusYears(2000)
}
