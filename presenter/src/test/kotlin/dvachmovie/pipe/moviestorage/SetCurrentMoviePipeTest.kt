package dvachmovie.pipe.moviestorage

import dvachmovie.TestException
import dvachmovie.db.data.NullMovie
import dvachmovie.usecase.moviestorage.SetCurrentMovieUseCase
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SetCurrentMoviePipeTest {

    @InjectMocks
    lateinit var pipe: SetCurrentMoviePipe

    @Mock
    lateinit var useCase: SetCurrentMovieUseCase

    private val testMovie = NullMovie()

    @Test
    fun `Happy pass`() {
        pipe.execute(testMovie)
    }

    @Test(expected = TestException::class)
    fun `Something was wrong`() {
        given(useCase.execute(testMovie)).willThrow(TestException())
        pipe.execute(testMovie)
    }
}
