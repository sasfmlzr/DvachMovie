package dvachmovie.pipe.utils

import dvachmovie.PresenterModel
import dvachmovie.architecture.PipeAsync
import dvachmovie.db.data.Movie
import dvachmovie.pipe.ShuffledMoviesModel
import dvachmovie.usecase.utils.ShuffleMoviesUseCase
import kotlinx.coroutines.channels.BroadcastChannel
import javax.inject.Inject

class ShuffleMoviesPipe @Inject constructor(
        private val broadcastChannel: BroadcastChannel<PresenterModel>,
        private val useCase: ShuffleMoviesUseCase) : PipeAsync<List<Movie>>() {

    override suspend fun execute(input: List<Movie>) {
        broadcastChannel.send(ShuffledMoviesModel(useCase.executeAsync(input)))
    }
}
