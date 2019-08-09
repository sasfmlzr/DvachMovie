package dvachmovie.pipe.db

import dvachmovie.architecture.PipeAsync
import dvachmovie.db.data.Thread
import dvachmovie.usecase.db.GetThreadsUseCase
import javax.inject.Inject

class GetHiddenThreadsPipe @Inject constructor(
        private val useCase: GetThreadsUseCase
) : PipeAsync<String, List<Thread>>() {

    override suspend fun execute(input: String): List<Thread> {
        return useCase.executeAsync(input).filter {
            it.isHidden
        }
    }
}
