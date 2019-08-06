package dvachmovie.pipe.db

import dvachmovie.architecture.PipeAsync
import dvachmovie.db.data.Thread
import dvachmovie.usecase.db.GetThreadsFromDBByNumUseCase
import dvachmovie.usecase.db.GetThreadsUseCase
import javax.inject.Inject

class GetHiddenThreadsPipe @Inject constructor(
        private val useCase: GetThreadsUseCase
) : PipeAsync<Unit, List<Thread>>() {

    override suspend fun execute(input: Unit): List<Thread> {
        return useCase.executeAsync(input).filter {
            it.isHidden
        }
    }
}
