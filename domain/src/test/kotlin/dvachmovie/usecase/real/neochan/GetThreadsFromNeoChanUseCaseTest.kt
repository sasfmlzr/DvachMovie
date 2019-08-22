package dvachmovie.usecase.real.neochan

import dvachmovie.TestException
import dvachmovie.architecture.logging.Logger
import dvachmovie.repository.NeoChanRepository
import dvachmovie.usecase.real.GetThreadsFromNeoChanUseCaseModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class GetThreadsFromNeoChanUseCaseTest {

    @InjectMocks
    private lateinit var useCase: GetThreadsFromNeoChanUseCase

    @Mock
    private lateinit var logger: Logger

    @Mock
    private lateinit var neoChanRepository: NeoChanRepository

    private val testValue = "test"

    private val testException = TestException()

    private val model = GetThreadsFromNeoChanUseCase.Params("test")

    @Test
    fun `Happy pass`() {
        val expectedValue = listOf(Pair(1, testValue))
        runBlocking {
            given(neoChanRepository.getNumThreadsFromCatalog("test")).willReturn(expectedValue)
            Assert.assertEquals(GetThreadsFromNeoChanUseCaseModel(expectedValue),
                    useCase.executeAsync(model))
        }
    }

    @Test(expected = TestException::class)
    fun `Error send to callback`() {
        runBlocking {
            given(neoChanRepository.getNumThreadsFromCatalog("test")).willThrow(testException)
            useCase.executeAsync(model)
        }
    }
}
