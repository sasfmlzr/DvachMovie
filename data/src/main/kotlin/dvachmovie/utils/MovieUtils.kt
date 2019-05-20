package dvachmovie.utils

import dvachmovie.api.FileItem
import dvachmovie.data.BuildConfig
import dvachmovie.db.data.Movie
import dvachmovie.db.data.MovieEntity
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat

object MovieUtils {

    fun shuffleMovies(movies: List<Movie>): List<Movie> =
            deleteIfMoviesIsPlayed(movies).shuffled()

    private fun deleteIfMoviesIsPlayed(movies: List<Movie>): List<Movie> =
            movies.filter { !it.isPlayed }

    fun calculateDiff(localList: List<Movie>,
                      dbList: List<Movie>): List<Movie> =
            dbList.filter { !localList.contains(it) && !it.isPlayed }

    fun getIndexPosition(currentMovie: Movie?, movieList: List<Movie>?): Int {
        if (currentMovie == null) throw RuntimeException("Current movie cannot be null")
        if (movieList == null) throw RuntimeException("Movie list cannot be null")

        movieList.indexOf(currentMovie).let {index ->
            return if (index == -1) 0 else index
        }
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
