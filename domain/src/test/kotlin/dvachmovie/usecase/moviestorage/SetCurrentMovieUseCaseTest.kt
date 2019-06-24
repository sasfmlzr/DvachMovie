package dvachmovie.usecase.moviestorage

import dvachmovie.TestException
import dvachmovie.db.data.NullMovie
import dvachmovie.storage.MovieStorage
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SetCurrentMovieUseCaseTest {

    @InjectMocks
    lateinit var useCase: SetCurrentMovieUseCase

    @Mock
    lateinit var movieStorage: MovieStorage

    private val testMovie = NullMovie()

    @Test
    fun `Happy pass`() {
        useCase.execute(testMovie)
    }

    @Test(expected = TestException::class)
    fun `Something was wrong`() {
        given(movieStorage.setCurrentMovieAndUpdate(testMovie)).willThrow(TestException())
        useCase.execute(testMovie)
    }
}
