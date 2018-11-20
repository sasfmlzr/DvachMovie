package dvachmovie.fragment.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dvachmovie.repository.local.MovieTempRepository
import javax.inject.Inject

class MovieViewModel @Inject constructor(movieTempRepository: MovieTempRepository) : ViewModel() {
    val uriMovie: MutableLiveData<List<String>> by lazy {
        MutableLiveData<List<String>>()
    }

    init {
        val movieUrl = movieTempRepository.movieLists.map { it.movieUrl }
        loadUri(movieUrl)
    }

    private fun loadUri(links: List<String>) {
        uriMovie.value = links
    }
}