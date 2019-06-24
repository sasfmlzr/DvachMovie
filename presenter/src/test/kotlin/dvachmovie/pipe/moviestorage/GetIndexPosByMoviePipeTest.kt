package dvachmovie.pipe.moviestorage

import dvachmovie.TestException
import dvachmovie.db.data.NullMovie
import dvachmovie.usecase.moviestorage.GetIndexPosByMovieUseCase
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetIndexPosByMoviePipeTest {

    @InjectMocks
    lateinit var pipe: GetIndexPosByMoviePipe

    @Mock
    lateinit var useCase: GetIndexPosByMovieUseCase

    private val testMovie = NullMovie()

    @Test
    fun `Happy pass`() {
        given(useCase.execute(testMovie)).willReturn(999)
        Assert.assertEquals(999, pipe.execute(testMovie))
    }

    @Test(expected = TestException::class)
    fun `Something was wrong`() {
        given(useCase.execute(testMovie)).willThrow(TestException())
        pipe.execute(testMovie)
    }
}
