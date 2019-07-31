package dvachmovie.pipe.db

import dvachmovie.architecture.PipeAsync
import dvachmovie.db.data.Movie
import dvachmovie.usecase.db.GetMoviesFromDBByBoardUseCase
import javax.inject.Inject

class GetMoviesFromDBByBoardPipe @Inject constructor(
        private val useCase: GetMoviesFromDBByBoardUseCase
) : PipeAsync<Pair<String, String>, List<Movie>>() {

    override suspend fun execute(input: Pair<String, String>): List<Movie> =
            useCase.executeAsync(input)

}
