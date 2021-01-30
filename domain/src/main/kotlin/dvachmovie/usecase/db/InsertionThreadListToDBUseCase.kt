package dvachmovie.usecase.db

import dvachmovie.db.data.Thread
import dvachmovie.repository.ThreadDBRepository
import dvachmovie.usecase.base.UseCase
import javax.inject.Inject

open class InsertionThreadListToDBUseCase @Inject constructor(
        private val threadDBRepository: ThreadDBRepository) : UseCase<List<Thread>, Unit>() {

    override suspend fun executeAsync(input: List<Thread>) {
        return threadDBRepository.insertAll(input)
    }
}
