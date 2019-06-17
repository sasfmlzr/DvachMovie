package dvachmovie.storage.local

import dvachmovie.db.data.Movie
import dvachmovie.db.data.NullMovie
import javax.inject.Inject

class LocalMovieStorage @Inject constructor() : MovieStorage {

    override var movieList: List<Movie> = mutableListOf()
        set(value) {
            field = value
            onMovieListChangedListener.onListChanged(value)
        }

    override lateinit var onMovieListChangedListener: OnMovieListChangedListener

    override var currentMovie: Movie = NullMovie()
        set(value) {
            field = value
            onMovieChangedListener.onMovieChanged(value)
        }

    override lateinit var onMovieChangedListener: OnMovieChangedListener
}
