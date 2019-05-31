package dvachmovie.usecase

import dvachmovie.db.data.Movie
import dvachmovie.repository.MovieDBRepository
import dvachmovie.usecase.base.UseCase
import javax.inject.Inject

class InsertionMovieToDBUseCase @Inject constructor(
        private val movieDBRepository: MovieDBRepository) : UseCase<Movie, Unit>() {

    override fun execute(input: Movie) =
        movieDBRepository.insert(input)
}
