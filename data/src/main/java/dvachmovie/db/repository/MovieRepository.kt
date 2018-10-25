package dvachmovie.db.repository

import dvachmovie.db.data.MovieEntity
import io.reactivex.Flowable

interface MovieRepository {

    fun getAll(): Flowable<MovieEntity>

    fun insert(movieEntity: MovieEntity)

    fun deleteAll()
}