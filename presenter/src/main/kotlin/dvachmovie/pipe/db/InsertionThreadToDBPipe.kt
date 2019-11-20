package dvachmovie.pipe.db

import dvachmovie.architecture.PipeAsync
import dvachmovie.db.data.Thread
import dvachmovie.usecase.db.InsertionThreadToDBUseCase
import javax.inject.Inject

class InsertionThreadToDBPipe @Inject constructor(
        private val useCase: InsertionThreadToDBUseCase
) : PipeAsync<Thread, Unit>() {

    override suspend fun execute(input: Thread) {
        val result = useCase.executeAsync(input)
        return result
    }
}
