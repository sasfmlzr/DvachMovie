package dvachmovie.repository

import dvachmovie.db.data.Movie

interface MovieDBRepository {
    fun getMoviesFromBoard(boardThread: String): List<Movie>
    fun insert(movieEntity: Movie)
    fun insertAll(moviesEntity: List<Movie>)
    fun deleteAll()
}
