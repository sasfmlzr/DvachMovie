package dvachmovie.usecase.db

import dvachmovie.TestException
import dvachmovie.db.data.NullThread
import dvachmovie.repository.ThreadDBRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class InsertionThreadListToDBUseCaseTest {

    @InjectMocks
    lateinit var useCase: InsertionThreadListToDBUseCase

    @Mock
    lateinit var threadDBRepository: ThreadDBRepository

    private val testThread = listOf(NullThread(1), NullThread(2))

    @Test
    fun `Happy pass`() {
        runBlocking {
            useCase.executeAsync(testThread)
        }
    }

    @Test(expected = TestException::class)
    fun `Something was wrong`() {
        runBlocking {
            given(threadDBRepository.insertAll(testThread)).willThrow(TestException())

            useCase.executeAsync(testThread)
        }
    }
}
