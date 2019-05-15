package dvachmovie.utils

import dvachmovie.api.FileItem
import dvachmovie.data.BuildConfig
import dvachmovie.db.data.MovieEntity
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat

object MovieUtils {

    fun shuffleMovies(movies: List<MovieEntity>): List<MovieEntity> =
            deleteIfMoviesIsPlayed(movies).shuffled()

    private fun deleteIfMoviesIsPlayed(movies: List<MovieEntity>): List<MovieEntity> =
            movies.filter { !it.isPlayed }

    fun calculateDiff(localList: List<MovieEntity>,
                      dbList: List<MovieEntity>): List<MovieEntity> =
            dbList.filter { !localList.contains(it) && !it.isPlayed }

    fun getIndexPosition(currentMovie: MovieEntity, movieList: List<MovieEntity>): Int =
            movieList.let {
                val index = it.indexOf(currentMovie)
                if (index == -1) {
                    0
                } else index
            }

    fun filterFileItemOnlyAsWebm(fileItems: List<FileItem>): List<FileItem> =
            fileItems.filter { it.path.contains(".webm") }

    fun convertFileItemToMovieEntity(fileItems: List<FileItem>, board: String): List<MovieEntity> =
            fileItems.map { fileItem ->
                MovieEntity(board = board,
                        movieUrl = BuildConfig.DVACH_URL + fileItem.path,
                        previewUrl = BuildConfig.DVACH_URL + fileItem.thumbnail,
                        date = parseDateFromFileItem(fileItem),
                        md5 = fileItem.md5,
                        thread = fileItem.numThread,
                        post = fileItem.numPost)
            }

    fun parseDateFromFileItem(fileItem: FileItem): LocalDateTime =
            LocalDateTime.parse(fileItem.date,
                    DateTimeFormat.forPattern
                    ("dd/MM/YYYY '${fileItem.date.substring(9, 12)}' HH:mm:ss"))
                    .plusYears(2000)
}
