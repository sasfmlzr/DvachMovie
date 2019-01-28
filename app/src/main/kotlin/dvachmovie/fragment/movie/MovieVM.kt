package dvachmovie.fragment.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dvachmovie.db.data.MovieEntity
import dvachmovie.repository.local.MovieRepository
import javax.inject.Inject

class MovieVM @Inject constructor(movieRepository: MovieRepository) : ViewModel() {

    val uriMovies: MutableLiveData<MutableList<MovieEntity>> = movieRepository.getMovies()

    val currentPos = MutableLiveData<Pair<Int, Long>>()

    fun getUrlList() = uriMovies

    val isPlayerControlVisibility: MutableLiveData<Boolean> = MutableLiveData()

    init {
        isPlayerControlVisibility.value = true
    }
}
