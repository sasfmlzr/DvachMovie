package dvachmovie.pipe.android

import dvachmovie.architecture.PipeSync
import dvachmovie.db.data.Movie
import dvachmovie.usecase.InsertionMovieToDBUseCase
import javax.inject.Inject

class InsertionMovieToDBPipe @Inject constructor(
        private val useCase: InsertionMovieToDBUseCase
) : PipeSync<Movie, Unit>() {

    override fun execute(input: Movie): Unit =
            useCase.execute(input)

}
