package dvachmovie.usecase.db

import dvachmovie.db.data.Movie
import dvachmovie.repository.MovieDBRepository
import dvachmovie.usecase.base.UseCase
import javax.inject.Inject

class GetMoviesFromDBByBoardUseCase @Inject constructor(
        private val movieDBRepository: MovieDBRepository) : UseCase<String, List<Movie>>() {

    override suspend fun executeAsync(input: String): List<Movie> {
        return movieDBRepository.getMoviesFromBoard(input)
    }
}
