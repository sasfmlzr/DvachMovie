package dvachmovie.storage

import dvachmovie.db.data.Movie
import dvachmovie.db.data.NullMovie
import javax.inject.Inject

class LocalMovieStorage @Inject constructor() : MovieStorage {

    private var innerMovieList: List<Movie> = listOf()

    override val movieList: List<Movie>
        get() = innerMovieList

    override fun setMovieListAndUpdate(movies: List<Movie>) {
        innerMovieList = movieList
        onMovieListChangedListener.onListChanged(movies)
    }

    override lateinit var onMovieListChangedListener: OnMovieListChangedListener

    private var innerCurrentMovie: Movie = NullMovie()

    override val currentMovie: Movie
        get() = innerCurrentMovie

    override fun setCurrentMovieAndUpdate(movie: Movie) {
        innerCurrentMovie = movie
        onMovieChangedListener.onMovieChanged(movie)
    }

    override lateinit var onMovieChangedListener: OnMovieChangedListener
}
