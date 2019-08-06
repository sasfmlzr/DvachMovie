package dvachmovie.usecase.db

import dvachmovie.db.data.Thread
import dvachmovie.repository.ThreadDBRepository
import dvachmovie.usecase.base.UseCase
import javax.inject.Inject

open class GetThreadsFromDBByNumUseCase @Inject constructor(
        private val threadDBRepository: ThreadDBRepository) : UseCase<Long, List<Thread>>() {

    override suspend fun executeAsync(input: Long): List<Thread> {
        return threadDBRepository.getThreadsByNumThread(input.toString())
    }
}
