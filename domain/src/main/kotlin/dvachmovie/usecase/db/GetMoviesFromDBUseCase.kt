package dvachmovie.usecase.db

import dvachmovie.db.data.Movie
import dvachmovie.repository.MovieDBRepository
import dvachmovie.usecase.base.UseCase
import javax.inject.Inject

open class GetMoviesFromDBUseCase @Inject constructor(
        private val moviesDBRepository: MovieDBRepository) : UseCase<Unit, List<Movie>>() {

    override suspend fun executeAsync(input: Unit): List<Movie> {
        return moviesDBRepository.getMovies()
    }
}
