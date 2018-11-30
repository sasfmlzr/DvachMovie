package dvachmovie.fragment.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dvachmovie.repository.local.Movie
import dvachmovie.repository.local.MovieTempRepository
import javax.inject.Inject

class MovieVM @Inject constructor(movieTempRepository: MovieTempRepository) : ViewModel() {
    private val uriMovie: MutableLiveData<MutableList<Movie>> = movieTempRepository.movieList

    private val currentPosition: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    fun getUrlList(): MutableLiveData<MutableList<Movie>> {
        return uriMovie
    }

    fun getPosition(): MutableLiveData<Int> {
        return currentPosition
    }
}
