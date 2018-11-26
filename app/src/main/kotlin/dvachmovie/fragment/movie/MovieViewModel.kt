package dvachmovie.fragment.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dvachmovie.repository.local.MovieTempRepository
import javax.inject.Inject

class MovieViewModel @Inject constructor(movieTempRepository: MovieTempRepository) : ViewModel() {
    val uriMovie: MutableLiveData<List<String>> by lazy {
        MutableLiveData<List<String>>()
    }
    val currentPosition: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    init {
        val movieUrl = movieTempRepository.movieLists.map { it.movieUrl }
        loadUri(movieUrl)
        var pos = 0
        if (movieUrl.contains(movieTempRepository.currentMovie.movieUrl)) {
            pos = movieUrl.indexOf(movieTempRepository.currentMovie.movieUrl)
        }

        loadCurrentPosition(pos)
    }

    private fun loadUri(links: List<String>) {
        uriMovie.value = links
    }

    private fun loadCurrentPosition(pos: Int) {
        currentPosition.value = pos
    }
}