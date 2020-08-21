package dvachmovie.pipe.moviestorage

import dvachmovie.TestException
import dvachmovie.db.data.Movie
import dvachmovie.storage.OnMovieListChangedListener
import dvachmovie.usecase.moviestorage.SetMovieListChangedListenerUseCase
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SetMovieListChangedListenerPipeTest {

    @InjectMocks
    lateinit var pipe: SetMovieListChangedListenerPipe

    @Mock
    lateinit var useCase: SetMovieListChangedListenerUseCase

    private val testListener = object : OnMovieListChangedListener {
        override fun onListChanged(movies: List<Movie>) = Unit
    }

    @Test
    fun `Happy pass`() {
        pipe.execute(testListener)
    }

    @Test(expected = TestException::class)
    fun `Something was wrong`() {
        given(useCase.execute(testListener)).willThrow(TestException())
        pipe.execute(testListener)
    }
}
