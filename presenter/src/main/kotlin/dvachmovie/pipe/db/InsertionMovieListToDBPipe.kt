package dvachmovie.pipe.db

import dvachmovie.architecture.PipeAsync
import dvachmovie.db.data.Movie
import dvachmovie.usecase.db.InsertionMovieListToDBUseCase
import javax.inject.Inject

class InsertionMovieListToDBPipe @Inject constructor(
        private val useCase: InsertionMovieListToDBUseCase
) : PipeAsync<List<Movie>, Unit>() {

    override suspend fun execute(input: List<Movie>) {
        return useCase.executeAsync(input)
    }
}
