package dvachmovie.fragment.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dvachmovie.repository.local.Movie
import dvachmovie.repository.local.MovieTempRepository
import javax.inject.Inject

class MovieVM @Inject constructor(movieTempRepository: MovieTempRepository) : ViewModel() {
    val uriMovie: MutableLiveData<MutableList<Movie>> = movieTempRepository.movieList

    val currentPosition: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
}