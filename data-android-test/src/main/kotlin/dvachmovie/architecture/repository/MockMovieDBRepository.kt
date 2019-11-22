package dvachmovie.architecture.repository

import dvachmovie.db.data.Movie
import dvachmovie.repository.MovieDBRepository
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
internal class MockMovieDBRepository @Inject constructor() : MovieDBRepository {

    override suspend fun getMovies(): List<Movie> {
        return listOf()
    }

    override suspend fun getMoviesFromBoard(baseUrl: String, boardThread: String): List<Movie> {
        return listOf()
    }

    override suspend fun insert(movieEntity: Movie) {
    }

    override suspend fun insertAll(moviesEntity: List<Movie>) {
    }

    override suspend fun deleteAll() {
    }

    override suspend fun deleteMovies(movies: List<Movie>) {
    }
}
