package DB.repository

import DB.data.MovieEntity
import io.reactivex.Flowable

interface MovieRepository {

    fun getAll(): Flowable<MovieEntity>

    fun insert(movieEntity: MovieEntity)

    fun deleteAll()
}