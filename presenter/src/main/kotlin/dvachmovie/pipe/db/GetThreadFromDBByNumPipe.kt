package dvachmovie.pipe.db

import dvachmovie.architecture.PipeAsync
import dvachmovie.db.data.Thread
import dvachmovie.usecase.db.GetThreadFromDBByNumUseCase
import javax.inject.Inject

class GetThreadFromDBByNumPipe @Inject constructor(
        private val useCase: GetThreadFromDBByNumUseCase
) : PipeAsync<Pair<Long, String>, Thread?>() {

    override suspend fun execute(input: Pair<Long, String>): Thread? =
            useCase.executeAsync(input)
}
