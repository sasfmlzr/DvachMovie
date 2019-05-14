package dvachmovie.usecase.real

import dvachmovie.TestException
import dvachmovie.architecture.logging.Logger
import dvachmovie.repository.DvachRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class GetThreadsFromDvachUseCaseTest {

    @InjectMocks
    private lateinit var useCase: GetThreadsFromDvachUseCase

    @Mock
    private lateinit var logger: Logger

    @Mock
    private lateinit var dvachRepository: DvachRepository

    private val listNumThreads = listOf("Test")

    private val testException = TestException()

    private val model = GetThreadsFromDvachUseCase.Params("test")

    @Test
    fun `Happy pass`() {
        runBlocking {
            given(dvachRepository.getNumThreadsFromCatalog("test")).willReturn(listNumThreads)
            Assert.assertEquals(GetThreadsFromDvachModel(listNumThreads), useCase.execute(model))
        }
    }

    @Test(expected = TestException::class)
    fun `Error send to callback`() {
        runBlocking {
            given(dvachRepository.getNumThreadsFromCatalog("test")).willThrow(testException)
            useCase.execute(model)
        }
    }
}
