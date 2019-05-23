package dvachmovie.repository

import androidx.lifecycle.LiveData
import dvachmovie.db.data.Movie

interface MovieDBRepository {
    fun getAll(): LiveData<List<Movie>>
    fun getMoviesFromBoard(boardThread: String): LiveData<List<Movie>>
    fun insert(movieEntity: Movie)
    fun insertAll(moviesEntity: List<Movie>)
    fun deleteAll()
}
