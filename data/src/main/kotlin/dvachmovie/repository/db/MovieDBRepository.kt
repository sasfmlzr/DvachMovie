package dvachmovie.repository.db

import androidx.lifecycle.LiveData
import dvachmovie.db.data.Movie
import dvachmovie.db.data.MovieEntity

interface MovieDBRepository {
    fun getAll(): LiveData<List<Movie>>
    fun getMoviesFromBoard(boardThread: String): LiveData<List<Movie>>
    fun insert(movieEntity: MovieEntity)
    fun insertAll(moviesEntity: List<MovieEntity>)
    fun deleteAll()
}
