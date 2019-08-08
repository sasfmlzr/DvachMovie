package dvachmovie.pipe.db

import dvachmovie.TestException
import dvachmovie.db.data.NullThread
import dvachmovie.db.data.Thread
import dvachmovie.usecase.db.GetThreadsUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetHiddenThreadsPipeTest {

    @InjectMocks
    lateinit var pipe: GetHiddenThreadsPipe

    @Mock
    lateinit var useCase: GetThreadsUseCase

    @Test
    fun `Happy pass`() {
        val testThreads = listOf<Thread>(NullThread(0),
                NullThread(1),
                NullThread(2))

        runBlocking {
            given(useCase.executeAsync("test")).willReturn(testThreads)
            val result = pipe.execute("test")
            Assert.assertEquals(testThreads, result)
        }
    }

    @Test (expected = TestException::class)
    fun `Something was wrong`() {
        runBlocking {
            given(useCase.executeAsync("test")).willThrow(TestException())
            pipe.execute("test")
        }
    }
}
