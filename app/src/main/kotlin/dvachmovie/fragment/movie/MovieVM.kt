package dvachmovie.fragment.movie

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dvachmovie.db.data.MovieEntity
import dvachmovie.repository.db.MovieDBRepository
import dvachmovie.repository.local.MovieTempRepository
import javax.inject.Inject

class MovieVM @Inject constructor(movieTempRepository: MovieTempRepository,
                                  movieDBRepository: MovieDBRepository) : ViewModel() {

    private val uriMovie: MediatorLiveData<List<MovieEntity>> = MediatorLiveData()
    val currentPos: MutableLiveData<Int> = MutableLiveData()

    init {
        uriMovie.value = mutableListOf()
        uriMovie.addSource(movieDBRepository.getAll()) {
            uriMovie.value = it
        }
    }

    fun getUrlList() = uriMovie
}