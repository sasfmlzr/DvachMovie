package dvachmovie.utils

import dvachmovie.api.FileItem
import dvachmovie.db.data.Movie

interface MovieUtils {

    fun shuffleMovies(movies: List<Movie>): List<Movie>

    fun calculateDiff(localList: List<Movie>,
                      dbList: List<Movie>): List<Movie>

    fun getIndexPosition(currentMovie: Movie?, movieList: List<Movie>?): Int

    fun filterFileItemOnlyAsMovie(fileItems: List<FileItem>): List<FileItem>

    fun sortByDate(movies: List<Movie>): List<Movie>
}
