package dvachmovie.pipe.db

import dvachmovie.architecture.PipeAsync
import dvachmovie.db.data.Thread
import dvachmovie.usecase.db.GetThreadsFromDBByNumUseCase
import javax.inject.Inject

class GetThreadsFromDBByNumPipe @Inject constructor(
        private val useCase: GetThreadsFromDBByNumUseCase
) : PipeAsync<Pair<Long, String>, List<Thread>>() {

    override suspend fun execute(input: Pair<Long, String>): List<Thread> =
            useCase.executeAsync(input)
}
