package dvachmovie.repository

import dvachmovie.db.data.Movie

interface MovieDBRepository {
    suspend fun getMoviesFromBoard(boardThread: String): List<Movie>
    suspend fun insert(movieEntity: Movie)
    suspend fun insertAll(moviesEntity: List<Movie>)
    suspend fun deleteAll()
}
