package dvachmovie.repository.db

import androidx.lifecycle.LiveData
import dvachmovie.db.data.MovieEntity

interface MovieRepository {
    fun getAll(): LiveData<List<MovieEntity>>
    fun getMoviesFromBoard(boardThread: String): LiveData<List<MovieEntity>>
    fun insert(movieEntity: MovieEntity)
    fun insertAll(moviesEntity: List<MovieEntity>)
    fun deleteAll()
}