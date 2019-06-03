package dvachmovie.pipe.android.moviestorage

import dvachmovie.architecture.PipeSync
import dvachmovie.db.data.Movie
import dvachmovie.usecase.moviestorage.GetIndexPosByMovieUseCase
import javax.inject.Inject

class GetIndexPosByMoviePipe @Inject constructor(
        private val useCase: GetIndexPosByMovieUseCase
) : PipeSync<Movie, Int>() {

    override fun execute(input: Movie): Int =
            useCase.execute(input)

}
