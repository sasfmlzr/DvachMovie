package dvachmovie.repository

import androidx.lifecycle.LiveData
import dvachmovie.db.data.Movie
import dvachmovie.db.model.MovieDao
import dvachmovie.db.model.MovieEntity
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
internal class LocalMovieDBRepository @Inject constructor(
        private val movieDao: MovieDao) : MovieDBRepository {

    override fun getAll() : LiveData<List<Movie>> = movieDao.getAll()  as LiveData<List<Movie>>

    override fun getMoviesFromBoard(boardThread: String): LiveData<List<Movie>> {
        return movieDao.getMoviesFromBoard(boardThread) as LiveData<List<Movie>>
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
