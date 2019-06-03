package dvachmovie.pipe.android

import dvachmovie.architecture.PipeSync
import dvachmovie.db.data.Movie
import dvachmovie.usecase.InsertionMovieListToDBUseCase
import javax.inject.Inject

class InsertionMovieListToDBPipe @Inject constructor(
        private val useCase: InsertionMovieListToDBUseCase
) : PipeSync<List<Movie>, Unit>() {

    override fun execute(input: List<Movie>): Unit =
            useCase.execute(input)

}
