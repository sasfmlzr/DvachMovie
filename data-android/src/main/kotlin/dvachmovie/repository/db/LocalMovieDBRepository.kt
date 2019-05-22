package dvachmovie.repository.db

import androidx.lifecycle.LiveData
import dvachmovie.db.data.Movie
import dvachmovie.db.data.MovieDao
import dvachmovie.db.data.MovieEntity
import javax.inject.Inject

internal class LocalMovieDBRepository @Inject constructor(
        private var movieDao: MovieDao) : MovieDBRepository {

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
