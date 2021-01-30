package dvachmovie.usecase.db

import dvachmovie.db.data.Thread
import dvachmovie.repository.ThreadDBRepository
import dvachmovie.usecase.base.UseCase
import javax.inject.Inject

open class InsertionThreadToDBUseCase @Inject constructor(
        private val threadDBRepository: ThreadDBRepository) : UseCase<Thread, Unit>() {

    override suspend fun executeAsync(input: Thread) {
        return threadDBRepository.insert(input)
    }
}
