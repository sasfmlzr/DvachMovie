package dvachmovie.usecase.db

import dvachmovie.repository.MovieDBRepository
import dvachmovie.repository.ThreadDBRepository
import dvachmovie.usecase.base.UseCase
import javax.inject.Inject

open class EraseDBUseCase @Inject constructor(
        private val movieDBRepository: MovieDBRepository,
        private val threadDBRepository: ThreadDBRepository) : UseCase<Unit, Unit>() {

    override suspend fun executeAsync(input: Unit) {
        movieDBRepository.deleteAll()
        threadDBRepository.deleteAll()
    }
}
