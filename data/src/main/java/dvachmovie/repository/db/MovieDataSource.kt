package dvachmovie.repository.db

import androidx.lifecycle.LiveData
import dvachmovie.db.data.MovieDao
import dvachmovie.db.data.MovieEntity
import javax.inject.Inject

class MovieDataSource @Inject constructor(private var movieDao: MovieDao) : MovieDBRepository {

    override fun getAll() = movieDao.getAll()


    override fun getMoviesFromBoard(boardThread: String): LiveData<List<MovieEntity>> {
        return movieDao.getMoviesFromBoard(boardThread)
    }

    override fun insert(movieEntity: MovieEntity) {
        movieDao.insert(movieEntity)
    }

    override fun insertAll(moviesEntity: List<MovieEntity>) {
        movieDao.insertAll(moviesEntity)
    }

    override fun deleteAll() {
        movieDao.deleteAll()
    }
}