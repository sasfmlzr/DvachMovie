package dvachmovie.utils

import dvachmovie.api.FileItem
import dvachmovie.db.data.Movie
import org.joda.time.LocalDateTime

interface MovieUtils {

    fun shuffleMovies(movies: List<Movie>): List<Movie>

    fun calculateDiff(localList: List<Movie>,
                      dbList: List<Movie>): List<Movie>

    fun getIndexPosition(currentMovie: Movie?, movieList: List<Movie>?): Int

    fun filterFileItemOnlyAsWebm(fileItems: List<FileItem>): List<FileItem>

    fun convertFileItemToMovie(fileItems: List<FileItem>, board: String): List<Movie>

    fun parseDateFromFileItem(fileItem: FileItem): LocalDateTime

    fun sortByDate(movies: List<Movie>): List<Movie>
}
