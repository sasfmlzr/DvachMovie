package dvachmovie.storage

import dvachmovie.db.data.Movie

interface MovieStorage {
    var movieList: List<Movie>

    var onMovieListChangedListener: OnMovieListChangedListener

    var currentMovie: Movie

    var onMovieChangedListener: OnMovieChangedListener
}

interface OnMovieListChangedListener {
    fun onListChanged(movies: List<Movie>)
}

interface OnMovieChangedListener {
    fun onMovieChanged(movie: Movie)
}
