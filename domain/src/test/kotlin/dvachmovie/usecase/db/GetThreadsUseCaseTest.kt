package dvachmovie.usecase.db

import dvachmovie.TestException
import dvachmovie.db.data.NullThread
import dvachmovie.repository.ThreadDBRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetThreadsUseCaseTest {

    @InjectMocks
    lateinit var useCase: GetThreadsUseCase

    @Mock
    lateinit var threadDBRepository: ThreadDBRepository

    private val testBaseUrl = "testBaseUrl"

    @Test
    fun `Happy pass`() {
        runBlocking {
            val expectedValue = listOf(NullThread(1), NullThread(2))
            given(threadDBRepository.getThreads(testBaseUrl)).willReturn(expectedValue)
            Assert.assertEquals(expectedValue, useCase.executeAsync(testBaseUrl))
        }
    }

    @Test(expected = TestException::class)
    fun `Something was wrong`() {
        runBlocking {
            given(threadDBRepository.getThreads(testBaseUrl)).willThrow(TestException())

            useCase.executeAsync(testBaseUrl)
        }
    }
}
