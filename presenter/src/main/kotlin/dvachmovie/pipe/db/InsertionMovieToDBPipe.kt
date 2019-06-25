package dvachmovie.pipe.db

import dvachmovie.architecture.PipeAsync
import dvachmovie.db.data.Movie
import dvachmovie.usecase.db.InsertionMovieToDBUseCase
import javax.inject.Inject

class InsertionMovieToDBPipe @Inject constructor(
        private val useCase: InsertionMovieToDBUseCase
) : PipeAsync<Movie>() {

    override suspend fun execute(input: Movie): Unit =
            useCase.executeAsync(input)

}
