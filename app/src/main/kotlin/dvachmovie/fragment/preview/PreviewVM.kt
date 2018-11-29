package dvachmovie.fragment.preview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dvachmovie.repository.local.Movie
import dvachmovie.repository.local.MovieTempRepository
import javax.inject.Inject

class PreviewVM @Inject constructor(movieTempRepository: MovieTempRepository) : ViewModel() {
    private val uriMovie = MutableLiveData<List<Movie>>()

    init {
        uriMovie.value = movieTempRepository.movieList.value?.map { movie -> movie }
    }

    fun getUriMovie() = uriMovie
}