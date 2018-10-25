package dvachmovie.db.repository

import dvachmovie.db.data.MovieDao
import dvachmovie.db.data.MovieEntity
import io.reactivex.Flowable
import javax.inject.Inject

class MovieDataSource @Inject constructor(private var movieDao: MovieDao) : MovieRepository {

    override fun getAll(): Flowable<MovieEntity> {
        return movieDao.getAll()
    }

    override fun insert(movieEntity: MovieEntity) {
        movieDao.insert(movieEntity)
    }

    override fun deleteAll() {
        movieDao.deleteAll()
    }
}