package dvachmovie.pipe.db

import dvachmovie.architecture.PipeAsync
import dvachmovie.db.data.Thread
import dvachmovie.usecase.db.InsertionThreadListToDBUseCase
import javax.inject.Inject

class InsertionThreadListToDBPipe @Inject constructor(
        private val useCase: InsertionThreadListToDBUseCase
) : PipeAsync<List<Thread>, Unit>() {

    override suspend fun execute(input: List<Thread>) {
        val result = useCase.executeAsync(input)
        return result
    }
}
