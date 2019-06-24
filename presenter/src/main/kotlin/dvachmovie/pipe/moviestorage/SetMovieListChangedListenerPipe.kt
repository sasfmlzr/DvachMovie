package dvachmovie.pipe.moviestorage

import dvachmovie.architecture.PipeSync
import dvachmovie.storage.OnMovieListChangedListener
import dvachmovie.usecase.moviestorage.SetMovieListChangedListenerUseCase
import javax.inject.Inject

class SetMovieListChangedListenerPipe @Inject constructor(
        private val useCase: SetMovieListChangedListenerUseCase
) : PipeSync<OnMovieListChangedListener, Unit>() {

    override fun execute(input: OnMovieListChangedListener): Unit =
            useCase.execute(input)
}
