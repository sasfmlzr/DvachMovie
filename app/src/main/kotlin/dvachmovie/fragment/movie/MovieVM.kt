package dvachmovie.fragment.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dvachmovie.repository.local.MovieTempRepository
import javax.inject.Inject

class MovieVM @Inject constructor(movieTempRepository: MovieTempRepository) : ViewModel() {
    val uriMovie: MutableLiveData<List<String>> by lazy {
        MutableLiveData<List<String>>()
    }
    val currentPosition: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    init {
        val movieUrl = movieTempRepository.movieLists.map { it.movieUrl }
        loadUri(movieUrl)
    }

    private fun loadUri(links: List<String>) {
        uriMovie.value = links
    }
}