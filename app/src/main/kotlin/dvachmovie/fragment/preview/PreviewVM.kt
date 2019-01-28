package dvachmovie.fragment.preview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dvachmovie.db.data.MovieEntity
import dvachmovie.repository.local.MovieRepository
import javax.inject.Inject

class PreviewVM @Inject constructor(movieRepository: MovieRepository) : ViewModel() {
    private val uriMovie = MutableLiveData<List<MovieEntity>>()

    init {
        uriMovie.value = movieRepository.getMovies().value?.map { movie -> movie }
    }

    fun getUriMovie() = uriMovie
}
