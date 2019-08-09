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
class GetThreadFromDBByNumUseCaseTest {

    @InjectMocks
    lateinit var useCase: GetThreadFromDBByNumUseCase

    @Mock
    lateinit var threadDBRepository: ThreadDBRepository

    private val testNumBoard = 3L
    private val testBaseUrl = "testBaseUrl"

    @Test
    fun `Happy pass`() {
        runBlocking {
            val expectedValue = NullThread(1)
            given(threadDBRepository.getThreadByNum(testNumBoard.toString(), testBaseUrl)).willReturn(expectedValue)
            Assert.assertEquals(expectedValue, useCase.executeAsync(Pair(testNumBoard, testBaseUrl)))
        }
    }

    @Test(expected = TestException::class)
    fun `Something was wrong`() {
        runBlocking {
            given(threadDBRepository.getThreadByNum(testNumBoard.toString(), testBaseUrl)).willThrow(TestException())

            useCase.executeAsync(Pair(testNumBoard, testBaseUrl))
        }
    }
}
