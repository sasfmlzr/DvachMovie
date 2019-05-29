package dvachmovie.pipe.utils

import dvachmovie.PresenterModel
import dvachmovie.architecture.ScopeProvider
import dvachmovie.db.data.Movie
import dvachmovie.pipe.Pipe
import dvachmovie.pipe.ShuffledMoviesModel
import dvachmovie.usecase.utils.ShuffleMoviesUseCase
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShuffleMoviesPipe @Inject constructor(
     //   private val broadcastChannel: BroadcastChannel<PresenterModel>,
        private val useCase: ShuffleMoviesUseCase,
        private val scopeProvider: ScopeProvider) : Pipe<List<Movie>>() {

    override fun execute(input: List<Movie>) {
        scopeProvider.ioScope.launch {
    //        broadcastChannel.send(ShuffledMoviesModel(useCase.execute(input)))
        }
    }
}
