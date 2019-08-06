package dvachmovie.usecase.db

import dvachmovie.db.data.Thread
import dvachmovie.repository.ThreadDBRepository
import dvachmovie.usecase.base.UseCase
import javax.inject.Inject

open class GetThreadsUseCase @Inject constructor(
        private val threadDBRepository: ThreadDBRepository) : UseCase<Unit, List<Thread>>() {

    override suspend fun executeAsync(input: Unit): List<Thread> {
        return threadDBRepository.getThreads()
    }
}
