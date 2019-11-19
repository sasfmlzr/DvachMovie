package dvachmovie.usecase.db

import dvachmovie.db.data.Movie
import dvachmovie.repository.MovieDBRepository
import dvachmovie.usecase.base.UseCase
import javax.inject.Inject

open class InsertionMovieListToDBUseCase @Inject constructor(
        private val movieDBRepository: MovieDBRepository) : UseCase<List<Movie>, Unit>() {

    override suspend fun executeAsync(input: List<Movie>) {
        val result = movieDBRepository.insertAll(input)
        return result
    }
}
