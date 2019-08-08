package dvachmovie.usecase.db

import dvachmovie.db.data.Thread
import dvachmovie.repository.ThreadDBRepository
import dvachmovie.usecase.base.UseCase
import javax.inject.Inject

open class GetThreadFromDBByNumUseCase @Inject constructor(
        private val threadDBRepository: ThreadDBRepository) : UseCase<Pair<Long, String>, Thread>() {

    override suspend fun executeAsync(input: Pair<Long, String>): Thread {
        return threadDBRepository.getThreadByNum(input.first.toString(), input.second)
    }
}
