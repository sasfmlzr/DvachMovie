package dvachmovie.pipe.android

import dvachmovie.architecture.PipeAsync
import dvachmovie.db.data.Movie
import dvachmovie.usecase.db.InsertionMovieListToDBUseCase
import javax.inject.Inject

class InsertionMovieListToDBPipe @Inject constructor(
        private val useCase: InsertionMovieListToDBUseCase
) : PipeAsync<List<Movie>>() {

    override suspend fun execute(input: List<Movie>): Unit =
            useCase.executeAsync(input)

}
