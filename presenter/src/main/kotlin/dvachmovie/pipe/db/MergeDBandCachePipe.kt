package dvachmovie.pipe.db

import dvachmovie.architecture.PipeSync
import dvachmovie.db.data.Movie
import dvachmovie.usecase.db.MergeDBandCacheUseCase
import javax.inject.Inject

class MergeDBandCachePipe @Inject constructor(
        private val useCase: MergeDBandCacheUseCase
) : PipeSync<List<Movie>, List<Movie>>() {

    override fun execute(input: List<Movie>): List<Movie> =
            useCase.execute(input)

}
