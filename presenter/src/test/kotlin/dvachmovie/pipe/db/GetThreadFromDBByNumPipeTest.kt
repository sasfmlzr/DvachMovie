package dvachmovie.pipe.db

import dvachmovie.TestException
import dvachmovie.db.data.NullThread
import dvachmovie.usecase.db.GetThreadFromDBByNumUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetThreadFromDBByNumPipeTest {

    @InjectMocks
    lateinit var pipe: GetThreadFromDBByNumPipe

    @Mock
    lateinit var useCase: GetThreadFromDBByNumUseCase

    @Test
    fun `Happy pass`() {
        val testValue = Pair(0L, "test")
        val expectedValue = NullThread(1)
        runBlocking {
            given(useCase.executeAsync(testValue)).willReturn(expectedValue)
            val thread = pipe.execute(testValue)
            Assert.assertEquals(expectedValue, thread)
        }
    }

    @Test(expected = TestException::class)
    fun `Something was wrong`() {
        val testValue = Pair(0L, "test")
        runBlocking {
            given(useCase.executeAsync(testValue)).willThrow(TestException())
            pipe.execute(testValue)
        }
    }
}
