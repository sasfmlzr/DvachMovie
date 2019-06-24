package dvachmovie.storage

import dvachmovie.db.data.Movie

interface MovieStorage {

    val movieList: List<Movie>

    fun setMovieListAndUpdate(movies: List<Movie>)

    var onMovieListChangedListener: OnMovieListChangedListener

    val currentMovie: Movie

    fun setCurrentMovieAndUpdate(movie: Movie)

    var onMovieChangedListener: OnMovieChangedListener
}

interface OnMovieListChangedListener {
    fun onListChanged(movies: List<Movie>)
}

interface OnMovieChangedListener {
    fun onMovieChanged(movie: Movie)
}
