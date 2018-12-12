package dvachmovie.fragment.preview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dvachmovie.db.data.MovieEntity
import dvachmovie.repository.db.MovieDBRepository
import javax.inject.Inject

class PreviewVM @Inject constructor(movieDBRepository: MovieDBRepository) : ViewModel() {
    private val uriMovie = MutableLiveData<List<MovieEntity>>()

    init {
        uriMovie.value = movieDBRepository.getAll().value?.map { movie -> movie }
    }

    fun getUriMovie() = uriMovie
}