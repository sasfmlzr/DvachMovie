package dvachmovie.usecase.db

import dvachmovie.TestException
import dvachmovie.db.data.NullMovie
import dvachmovie.repository.MovieDBRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetMoviesFromDBUseCaseTest {

    @InjectMocks
    lateinit var useCase: GetMoviesFromDBUseCase

    @Mock
    lateinit var movieDBRepository: MovieDBRepository

    @Test
    fun `Happy pass`() {
        runBlocking {
            val expectedValue = listOf(NullMovie())
            given(movieDBRepository.getMovies()).willReturn(expectedValue)
            Assert.assertEquals(expectedValue, useCase.executeAsync(Unit))
        }
    }

    @Test(expected = TestException::class)
    fun `Something was wrong`() {
        runBlocking {
            given(movieDBRepository.getMovies()).willThrow(TestException())

            useCase.executeAsync(Unit)
        }
    }
}
