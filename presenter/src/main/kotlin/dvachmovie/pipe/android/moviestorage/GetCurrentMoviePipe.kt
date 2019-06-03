package dvachmovie.pipe.android.moviestorage

import androidx.lifecycle.MutableLiveData
import dvachmovie.architecture.PipeSync
import dvachmovie.db.data.Movie
import dvachmovie.usecase.moviestorage.GetCurrentMovieUseCase
import javax.inject.Inject

class GetCurrentMoviePipe @Inject constructor(
        private val useCase: GetCurrentMovieUseCase
) : PipeSync<Unit, MutableLiveData<Movie>>() {

    override fun execute(input: Unit): MutableLiveData<Movie> =
            useCase.execute(input)

}
