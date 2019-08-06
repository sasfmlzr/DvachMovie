package dvachmovie.pipe.db

import dvachmovie.architecture.PipeAsync
import dvachmovie.db.data.Thread
import dvachmovie.usecase.db.GetThreadsFromDBByNumUseCase
import javax.inject.Inject

class GetThreadsFromDBByNumPipe @Inject constructor(
        private val useCase: GetThreadsFromDBByNumUseCase
) : PipeAsync<Long, List<Thread>>() {

    override suspend fun execute(input: Long): List<Thread> =
            useCase.executeAsync(input)
}
