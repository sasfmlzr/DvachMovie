package dvachmovie.usecase.moviestorage

import dvachmovie.db.data.Movie
import dvachmovie.storage.MovieStorage
import dvachmovie.storage.OnMovieListChangedListener
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SetMovieListChangedListenerUseCaseTest {

    @InjectMocks
    lateinit var useCase: SetMovieListChangedListenerUseCase

    @Mock
    lateinit var movieStorage: MovieStorage

    @Test
    fun `Happy pass`() {
        val testListener = object : OnMovieListChangedListener {
            override fun onListChanged(movies: List<Movie>) {
            }
        }
        useCase.execute(testListener)
    }
}
