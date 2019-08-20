package dvachmovie.usecase.db

import dvachmovie.TestException
import dvachmovie.db.data.NullMovie
import dvachmovie.repository.MovieDBRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DeleteMoviesUseCaseTest {

    @InjectMocks
    lateinit var useCase: DeleteMoviesUseCase

    @Mock
    lateinit var movieDBRepository: MovieDBRepository

    private val testData = listOf(NullMovie())

    @Test
    fun `Happy pass`() {
        runBlocking {
            useCase.executeAsync(testData)
        }
    }

    @Test(expected = TestException::class)
    fun `Something was wrong`() {
        runBlocking {
            given(movieDBRepository.deleteMovies(testData)).willThrow(TestException())
            useCase.executeAsync(testData)
        }
    }
}
