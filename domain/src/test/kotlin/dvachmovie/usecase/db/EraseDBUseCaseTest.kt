package dvachmovie.usecase.db

import dvachmovie.TestException
import dvachmovie.repository.MovieDBRepository
import dvachmovie.repository.ThreadDBRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class EraseDBUseCaseTest {

    @InjectMocks
    lateinit var useCase: EraseDBUseCase

    @Mock
    lateinit var movieDBRepository: MovieDBRepository

    @Mock
    lateinit var threadDBRepository: ThreadDBRepository

    @Test
    fun `Happy pass`() {
        runBlocking {
            useCase.executeAsync(Unit)
        }
    }

    @Test(expected = TestException::class)
    fun `Something was wrong`() {
        runBlocking {
            given(movieDBRepository.deleteAll()).willThrow(TestException())

            useCase.executeAsync(Unit)
        }
    }
}
