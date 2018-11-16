package dvachmovie.fragment.movie

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
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