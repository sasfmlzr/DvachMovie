package dvachmovie.repository

import dvachmovie.db.data.Movie
import dvachmovie.db.model.MovieDao
import dvachmovie.db.model.MovieEntity
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
internal class LocalMovieDBRepository @Inject constructor(
        private val movieDao: MovieDao) : MovieDBRepository {

    override suspend fun getMoviesFromBoard(baseUrl: String, boardThread: String): List<Movie> {
        return movieDao.getMoviesFromBoard(boardThread, baseUrl)
    }

    override suspend fun insert(movieEntity: Movie) {
        movieDao.insert(movieEntity as MovieEntity)
    }

    override suspend fun insertAll(moviesEntity: List<Movie>) {
        movieDao.insertAll(moviesEntity as List<MovieEntity>)
    }

    override suspend fun deleteAll() {
        movieDao.deleteAll()
    }

    override suspend fun deleteMovies(movies: List<Movie>) {
        movieDao.deleteMovies(movies as List<MovieEntity>)
    }
}
