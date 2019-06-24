package dvachmovie.usecase.moviestorage

import dvachmovie.db.data.Movie
import dvachmovie.storage.MovieStorage
import dvachmovie.storage.OnMovieChangedListener
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SetMovieChangedListenerUseCaseTest {


    @InjectMocks
    lateinit var useCase: SetMovieChangedListenerUseCase

    @Mock
    lateinit var movieStorage: MovieStorage

    @Test
    fun `Happy pass`() {
        val testListener = object : OnMovieChangedListener {
            override fun onMovieChanged(movie: Movie) {
            }
        }
        useCase.execute(testListener)
    }
}
