package dvachmovie.usecase.db

import dvachmovie.db.data.Movie
import dvachmovie.repository.MovieDBRepository
import dvachmovie.usecase.base.UseCase
import javax.inject.Inject

open class InsertionMovieToDBUseCase @Inject constructor(
        private val movieDBRepository: MovieDBRepository) : UseCase<Movie, Unit>() {

    override suspend fun executeAsync(input: Movie) =
        movieDBRepository.insert(input)
}
