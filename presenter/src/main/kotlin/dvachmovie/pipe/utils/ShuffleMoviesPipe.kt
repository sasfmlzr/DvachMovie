package dvachmovie.pipe.utils

import dvachmovie.PresenterModel
import dvachmovie.architecture.PipeAsync
import dvachmovie.db.data.Movie
import dvachmovie.pipe.ShuffledMoviesModel
import dvachmovie.usecase.utils.ShuffleMoviesUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

class ShuffleMoviesPipe @Inject constructor(
        private val broadcastChannel: MutableSharedFlow<PresenterModel>,
        private val useCase: ShuffleMoviesUseCase) : PipeAsync<List<Movie>, Unit>() {

    override suspend fun execute(input: List<Movie>) {
        broadcastChannel.emit(ShuffledMoviesModel(useCase.executeAsync(input)))
    }
}
