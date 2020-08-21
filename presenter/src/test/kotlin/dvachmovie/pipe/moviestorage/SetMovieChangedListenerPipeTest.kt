package dvachmovie.pipe.moviestorage

import dvachmovie.TestException
import dvachmovie.db.data.Movie
import dvachmovie.storage.OnMovieChangedListener
import dvachmovie.usecase.moviestorage.SetMovieChangedListenerUseCase
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SetMovieChangedListenerPipeTest {

    @InjectMocks
    lateinit var pipe: SetMovieChangedListenerPipe

    @Mock
    lateinit var useCase: SetMovieChangedListenerUseCase

    private val testListener = object : OnMovieChangedListener {
        override fun onMovieChanged(movie: Movie) = Unit
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
