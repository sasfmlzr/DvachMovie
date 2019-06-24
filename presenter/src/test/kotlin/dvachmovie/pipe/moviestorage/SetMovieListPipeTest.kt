package dvachmovie.pipe.moviestorage

import dvachmovie.TestException
import dvachmovie.db.data.NullMovie
import dvachmovie.usecase.moviestorage.SetMovieListUseCase
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SetMovieListPipeTest {

    @InjectMocks
    lateinit var pipe: SetMovieListPipe

    @Mock
    lateinit var useCase: SetMovieListUseCase

    private val testListMovies = listOf(NullMovie(), NullMovie("test"))

    @Test
    fun `Happy pass`() {
        pipe.execute(testListMovies)
    }

    @Test(expected = TestException::class)
    fun `Something was wrong`() {
        given(useCase.execute(testListMovies)).willThrow(TestException())
        pipe.execute(testListMovies)
    }
}
