package dvachmovie.pipe.android.moviestorage

import androidx.lifecycle.MutableLiveData
import dvachmovie.architecture.PipeSync
import dvachmovie.db.data.Movie
import dvachmovie.usecase.moviestorage.GetMovieListUseCase
import javax.inject.Inject

class GetMovieListPipe @Inject constructor(
        private val useCase: GetMovieListUseCase
) : PipeSync<Unit, MutableLiveData<List<Movie>>>() {

    override fun execute(input: Unit): MutableLiveData<List<Movie>> =
            useCase.execute(input)

}
