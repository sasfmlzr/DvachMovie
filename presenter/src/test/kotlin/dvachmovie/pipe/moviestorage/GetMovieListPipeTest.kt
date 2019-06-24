package dvachmovie.pipe.moviestorage

import dvachmovie.TestException
import dvachmovie.db.data.NullMovie
import dvachmovie.usecase.moviestorage.GetMovieListUseCase
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetMovieListPipeTest {

    @InjectMocks
    lateinit var pipe: GetMovieListPipe

    @Mock
    lateinit var useCase: GetMovieListUseCase

    private val testListMovies = listOf(NullMovie(), NullMovie("test"))

    @Test
    fun `Happy pass`() {
        given(useCase.execute(Unit)).willReturn(testListMovies)
        Assert.assertEquals(testListMovies, pipe.execute(Unit))
    }

    @Test(expected = TestException::class)
    fun `Something was wrong`() {
        given(useCase.execute(Unit)).willThrow(TestException())
        pipe.execute(Unit)
    }
}
