package dvachmovie.repository

import dvachmovie.db.data.Movie

interface MovieDBRepository {
    suspend fun getMoviesFromBoard(baseUrl: String, boardThread: String): List<Movie>
    suspend fun insert(movieEntity: Movie)
    suspend fun insertAll(moviesEntity: List<Movie>)
    suspend fun deleteAll()
    suspend fun deleteMovies(movies: List<Movie>)
}
