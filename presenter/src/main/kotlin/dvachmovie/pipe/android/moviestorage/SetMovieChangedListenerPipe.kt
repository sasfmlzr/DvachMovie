package dvachmovie.pipe.android.moviestorage

import dvachmovie.architecture.PipeSync
import dvachmovie.storage.local.OnMovieChangedListener
import dvachmovie.usecase.moviestorage.SetMovieChangedListenerUseCase
import javax.inject.Inject

class SetMovieChangedListenerPipe @Inject constructor(
        private val useCase: SetMovieChangedListenerUseCase
) : PipeSync<OnMovieChangedListener, Unit>() {

    override fun execute(input: OnMovieChangedListener): Unit =
            useCase.execute(input)
}
