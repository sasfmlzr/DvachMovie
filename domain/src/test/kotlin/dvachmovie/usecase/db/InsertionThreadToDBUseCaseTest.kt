package dvachmovie.usecase.db

import dvachmovie.TestException
import dvachmovie.db.data.NullThread
import dvachmovie.repository.ThreadDBRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class InsertionThreadToDBUseCaseTest {

    @InjectMocks
    lateinit var useCase: InsertionThreadToDBUseCase

    @Mock
    lateinit var threadDBRepository: ThreadDBRepository

    private val testThread = NullThread(1)

    @Test
    fun `Happy pass`() {
        runBlocking {
            useCase.executeAsync(testThread)
        }
    }

    @Test(expected = TestException::class)
    fun `Something was wrong`() {
        runBlocking {
            BDDMockito.given(threadDBRepository.insert(testThread)).willThrow(TestException())

            useCase.executeAsync(testThread)
        }
    }
}
