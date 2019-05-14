package dvachmovie.repository.local

import dvachmovie.db.data.MovieEntity

class MovieUtils {
    companion object {
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
    }
}
