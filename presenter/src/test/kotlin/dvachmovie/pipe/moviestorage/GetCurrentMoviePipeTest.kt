package dvachmovie.pipe.moviestorage

import dvachmovie.TestException
import dvachmovie.db.data.NullMovie
import dvachmovie.usecase.moviestorage.GetCurrentMovieUseCase
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetCurrentMoviePipeTest {

    @InjectMocks
    lateinit var pipe: GetCurrentMoviePipe

    @Mock
    lateinit var useCase: GetCurrentMovieUseCase

    private val testMovie = NullMovie()

    @Test
    fun `Happy pass`() {
        given(useCase.execute(Unit)).willReturn(testMovie)
        Assert.assertEquals(testMovie, pipe.execute(Unit))
    }

    @Test(expected = TestException::class)
    fun `Something was wrong`() {
        given(useCase.execute(Unit)).willThrow(TestException())
        pipe.execute(Unit)
    }
}
