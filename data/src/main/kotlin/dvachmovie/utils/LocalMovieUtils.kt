package dvachmovie.utils

import dvachmovie.api.FileItem
import dvachmovie.db.data.Movie
import javax.inject.Inject

class LocalMovieUtils @Inject constructor() : MovieUtils {

    override fun shuffleMovies(movies: List<Movie>): List<Movie> =
            deleteIfMoviesIsPlayed(movies).shuffled()

    private fun deleteIfMoviesIsPlayed(movies: List<Movie>): List<Movie> =
            movies.filter { !it.isPlayed }

    override fun calculateDiff(localList: List<Movie>,
                               dbList: List<Movie>): List<Movie> =
            dbList.filter {!localList.contains(it)}

    override fun getIndexPosition(currentMovie: Movie?, movieList: List<Movie>?): Int {
        if (currentMovie == null) throw RuntimeException("Current movie cannot be null")
        if (movieList == null) throw RuntimeException("Movie list cannot be null")

        movieList.indexOf(currentMovie).let { index ->
            return if (index == -1) 0 else index
        }
    }

    override fun filterFileItemOnlyAsWebm(fileItems: List<FileItem>): List<FileItem> =
            fileItems.filter { it.path.contains(".webm") }

    override fun sortByDate(movies: List<Movie>): List<Movie> {
        return movies.sortedByDescending { it.date }
    }
}
