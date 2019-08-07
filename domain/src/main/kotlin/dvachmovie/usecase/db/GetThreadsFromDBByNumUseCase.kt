package dvachmovie.usecase.db

import dvachmovie.db.data.Thread
import dvachmovie.repository.ThreadDBRepository
import dvachmovie.usecase.base.UseCase
import javax.inject.Inject

open class GetThreadsFromDBByNumUseCase @Inject constructor(
        private val threadDBRepository: ThreadDBRepository) : UseCase<Pair<Long, String>, List<Thread>>() {

    override suspend fun executeAsync(input: Pair<Long, String>): List<Thread> {
        return threadDBRepository.getThreadsByNumThread(input.first.toString(), input.second)
    }
}
