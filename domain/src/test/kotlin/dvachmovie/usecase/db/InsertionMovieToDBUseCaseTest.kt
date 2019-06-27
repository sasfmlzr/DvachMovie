package dvachmovie.usecase.db

import dvachmovie.TestException
import dvachmovie.db.data.NullMovie
import dvachmovie.repository.MovieDBRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.doNothing
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class InsertionMovieToDBUseCaseTest {

    @InjectMocks
    lateinit var useCase: InsertionMovieToDBUseCase

    @Mock
    lateinit var movieDBRepository: MovieDBRepository

    private val movie = NullMovie("test")

    @Test
    fun `Happy pass`() {
        runBlocking {
            useCase.executeAsync(movie)
        }
    }

    @Test(expected = TestException::class)
    fun `Something was wrong`() {
        runBlocking {
            given(movieDBRepository.insert(movie)).willThrow(TestException())

            useCase.executeAsync(movie)
        }
    }
}
