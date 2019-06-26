package dvachmovie.pipe.db

import dvachmovie.architecture.PipeAsync
import dvachmovie.db.data.Movie
import dvachmovie.usecase.db.GetMoviesFromDBByBoardUseCase
import javax.inject.Inject

class GetMoviesFromDBByBoardPipe @Inject constructor(
        private val useCase: GetMoviesFromDBByBoardUseCase
) : PipeAsync<String, List<Movie>>() {

    override suspend fun execute(input: String): List<Movie> =
            useCase.executeAsync(input)

}
