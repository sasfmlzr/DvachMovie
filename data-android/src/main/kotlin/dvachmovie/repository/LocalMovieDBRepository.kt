package dvachmovie.repository

import dvachmovie.db.data.Movie
import dvachmovie.db.model.MovieDao
import dvachmovie.db.model.MovieEntity
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
internal class LocalMovieDBRepository @Inject constructor(
        private val movieDao: MovieDao) : MovieDBRepository {

    override fun getMoviesFromBoard(boardThread: String): List<Movie> {
        return movieDao.getMoviesFromBoard(boardThread)
    }

    override fun insert(movieEntity: Movie) {
        movieDao.insert(movieEntity as MovieEntity)
    }

    override fun insertAll(moviesEntity: List<Movie>) {
        movieDao.insertAll(moviesEntity as List<MovieEntity>)
    }

    override fun deleteAll() {
        movieDao.deleteAll()
    }
}
