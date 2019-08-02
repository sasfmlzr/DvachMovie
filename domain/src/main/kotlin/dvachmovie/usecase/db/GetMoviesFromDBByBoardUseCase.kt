package dvachmovie.usecase.db

import dvachmovie.db.data.Movie
import dvachmovie.repository.MovieDBRepository
import dvachmovie.usecase.base.UseCase
import javax.inject.Inject

open class GetMoviesFromDBByBoardUseCase @Inject constructor(
        private val movieDBRepository: MovieDBRepository) : UseCase<Pair<String, String>, List<Movie>>() {

    override suspend fun executeAsync(input: Pair<String, String>): List<Movie> {
        return movieDBRepository.getMoviesFromBoard(input.first, input.second)
    }
}
