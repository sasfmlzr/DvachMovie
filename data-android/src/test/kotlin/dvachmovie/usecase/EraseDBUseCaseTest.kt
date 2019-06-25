package dvachmovie.usecase

import dvachmovie.TestException
import dvachmovie.repository.MovieDBRepository
import dvachmovie.usecase.db.EraseDBUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.doNothing
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class EraseDBUseCaseTest {

    @InjectMocks
    lateinit var eraseDBUseCase: EraseDBUseCase

    @Mock
    lateinit var movieDBRepository: MovieDBRepository

    @Test
    fun `Happy pass`() {
        runBlocking {
            doNothing().`when`(movieDBRepository).deleteAll()

            eraseDBUseCase.execute(Unit)
        }
    }

    @Test(expected = TestException::class)
    fun `Something was wrong`() {
        runBlocking {
            given(movieDBRepository.deleteAll()).willThrow(TestException())

            eraseDBUseCase.execute(Unit)
        }
    }
}
