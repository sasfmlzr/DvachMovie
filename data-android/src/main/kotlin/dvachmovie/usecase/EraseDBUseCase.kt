package dvachmovie.usecase

import dvachmovie.repository.MovieDBRepository
import dvachmovie.usecase.base.UseCase
import javax.inject.Inject

class EraseDBUseCase @Inject constructor(
        private val movieDBRepository: MovieDBRepository) : UseCase<Unit, Unit>() {

    override suspend fun execute(input: Unit) =
        movieDBRepository.deleteAll()
}
