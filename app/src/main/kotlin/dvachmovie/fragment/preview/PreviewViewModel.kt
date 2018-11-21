package dvachmovie.fragment.preview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dvachmovie.repository.local.MovieTempRepository
import javax.inject.Inject

class PreviewViewModel @Inject constructor(movieTempRepository: MovieTempRepository) : ViewModel() {
    private val uriMovie = MutableLiveData<List<String>>()

    init {
        uriMovie.value = movieTempRepository.movieLists.map { movie -> movie.moviePreviewUrl }
    }

    fun getUriMovie() = uriMovie

}